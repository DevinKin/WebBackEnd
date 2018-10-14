package cn.devinkin.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

public class LoginController {
    public static void main(String[] args) throws IOException {
        LoginService proxy = RPC.getProxy(LoginService.class, 1L, new InetSocketAddress("devinkin", 11111), new Configuration());
        String result = proxy.login("angelababy", "123456");
        System.out.println(result);
    }
}
