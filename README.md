# when

# TODO
1. invoker可能单例也可能多例
2. 同一个operation的不同属性，invoker可能都会定制，同时需要注意如何定位这个operation是这个invoker的
3. 有些operation在运行前就能确定（因为有些准备工作，比方说编译），有些在运行时传入的fact确定？（如果运行时确定的话，那还不如运行前确定的写的灵活一点，根据运行时传入的fact鞋不同的逻辑）