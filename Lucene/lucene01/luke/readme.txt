运行lukeall，如果需要加载第三方分词器，需通过-Djava.ext.dirs加载jar包:
可简单的将第三方分词器和lukeall放在一块儿，cmd下运行：

java -Djava.ext.dirs=. -jar lukeall-4.10.3.jar