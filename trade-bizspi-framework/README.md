### 什么是业务编排引擎

- 在面对复杂流程时，如何更清晰明了地，把这些流程节点进行合理拆分并进行编排（类似还有流程引擎）。
- 每个流程节点在不同业务下，有不同的业务逻辑表现。面对这种变化，后期需求来了，如何对修改关闭，对扩展开放。
- 电商、大促，未来在涉及到稳定性与大促保障，其中部分功能节点是可以做降级，如何更好地针对部分业务流程部分功能点进行降级。
- 目的就是在复杂流程下：更好地组织业务流程、更好地编排功能、更好地维护代码、更好地业务扩展。

![流程编排](https://s.xinc818.com/files/khjxwkg6mow0fh.png)

在一个复杂业务场景，业务流是由多个需要扩展、编排的业务扩展点组成。通过实现框架的接口并注册扩展点实现给框架，可以编排整个业务流。

### 使用方式

#### 引入依赖
    <dependency>
        <groupId>com.vip8</groupId>
        <artifactId>trade-bizspi-framework-core</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
    
#### 功能讲解

业务流的定义首先需要定义业务流扩展点，自定义业务流扩展点接口，并且继承 IBaseSpi 接口。并且在接口上标注 @SpiFunctionPoint 注释。其中 spiBeanName
 是必须设置的属性，这个属性指定了在注入进 spring 容器以后，在容器中的 bean id。在 spring 容器启动以后，可以像正常的 bean 一样去使用。
 
下一步是对业务扩展点绑定扩展点实现，扩展点实现都继承上述的自定义业务扩展点接口，并且标注 @BizSpiImpl 接口，以将扩展点实现注册给框架。接口的方法中 
filter 返回 true/false，用来控制是否执行该实现。invoke 方法是具体是扩展点实现逻辑。 

扩展点和扩展点实现的扫描通过标注 @SpiFunctionPointScan 注解指定，basePackages 属性指定了扫描路径。

#### 举例
1、使用注解@SpiFunctionPointScan配置扫描的包路径
```
@SpringBootApplication
@SpiFunctionPointScan("com.vip8.trade.bizspi.sample")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
2、功能节点接口使用@SpiFunctionPoint标记
```
@SpiFunctionPoint("计价，本地计价、远程优惠计价、不需要计价等多种不同计价方式")
public interface CalcPrice extends IBaseSpi<GroupOrderSDTO, Integer> {
}
```
3、使用@BizSpi标记Spi功能点接口实现
```
@BizSpi
public class GeneralCalcPrice implements CalcPrice {
    @Override
    public boolean filter(GroupOrderSDTO groupOrderSDTO) {
        return true;
    }
    @Override
    public List<Integer> invoke(GroupOrderSDTO groupOrderSDTO) {
        return Lists.newArrayList(100_00);
    }
}
```
#### 如何控制执行顺序

扩展点接口（IBaseSpi）的 config 方法，返回的 SpiConfig 可以指定执行顺序，当有一个扩展点有多个实现时有用。执行顺序，按照值从小到大往后执行。
priority 值越小越先执行，值越大越靠后执行。

#### 如何控制互斥执行

当一个扩展点有多个实现，如果只希望有一个实现被执行，可以通过指定 @SpiFunctionPoint 注解的 mutex 为 true（默认值），这样只要匹配并执行了一
个扩展点实现，那么就会中断，后续的实现都不会被执行。

#### 执行上下文

IBaseSpi 接口定义了，一个业务扩展点，业务扩展点的执行入口是 invoke 方法：
```
public interface IBaseSpi<In, Out> {
    /**
     * spi执行内容
     *
     * @param ctx spi上下文
     * @return spi执行结果组合
     */
    List<Out> invoke(In ctx);
}
```
泛型 In 定义整个业务流的执行上下文，使用的时候可以根据自己的需要定义并初始化自己的执行上下文，比如交易的下单，执行上下文中会放入前端请求参数、
父子订单、优惠信息、sku信息等。

一个业务流的多个业务扩展点，需要将执行上下文定义为同一个接口，方便共享一个执行上下文。

#### 执行返回

IBaseSpi 的 invoke 方法返回的是一个 List，意味着业务扩展点的执行可以返回多个对象。那么如果一个扩展点有多个实现，并且不设置互斥，并且又都去执行，
会怎么返回结果呢？答案是，所有的扩展点实现返回的 List 合并到一个 List 返回。