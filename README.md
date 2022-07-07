# 完成功能

前端提供给用户的功能，与 aw03 类似，即一个拥有基本功能的 WebPOS。

添加了订单查看功能，即用户点击 Charge 按钮之后，就会提交相应的订单信息，在页面的 Delivery 处可以进行查看。

# 功能特点

除了单独用于处理亚马逊商品数据的 `batch` 项目之外，所有其他项目都是 `micropos` 项目的子项目，具体组成部分如下：

- `pos-products`：获取数据库中的商品信息
- `pos-carts`：维护用户的购物车信息
- `pos-order`：提交用户订单信息到 RabbitMQ 中
- `pos-discovery`：微服务架构的 Discovery 服务
- `pos-gateway`：微服务架构的 API 网关
- `pos-delivery`：异步收到来自于 RabbitMQ 的消息，用于展示所有的订单信息
- `pos-integration`：使用 Spring Integration 展示订单信息。
- `pos-webui`：即 aw04 中实现的 WebPOS，使用 thymeleaf 作为前端

通过微服务架构，除了 `pos-discovery` 和 `pos-gateway` 之外，所有其他微服务都可以启动多个实例，从而得到了良好的拓展性。