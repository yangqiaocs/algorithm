Gossip协议
首先，简单描述一下这个协议，它以一种广播的方式将自己的消息在整个节点之间进行传播，因此它是一种去中心化的架构，应用于分布式架构中的横向扩展时，不同节点间的数据一致性问题，但是它支持的不是强一致性，而是最终一致性

#### 消息传播
在节点接收到新的消息（病毒）之后，就会向它能够接触到的节点传播消息，当收到消息后，新的节点也会将消息广播出去。因此，会出现重复收到消息问题，此时需要使用消息的版本号控制来避免旧的消息一直在网络中传输

#### 一致性
由于节点间发送消息会存在延迟，并且并不是端到端的消息传送，洪泛法传播消息一定会导致部分节点的消息之后，但是其也能保证最终每个节点的消息是一致的，适用于，信息不是经常变化，但是对实时性要求又较高的情况。
为什么适用于实时性较高的情况呢？因为广播式的传送消息不需要建立连接，有消息就会被收到

如果节点之间共同维护一个路由表，那么每个节点更新后就可以主动去更新这个路由表，这样在更新消息时就不会有那么多的消息一直在网络中