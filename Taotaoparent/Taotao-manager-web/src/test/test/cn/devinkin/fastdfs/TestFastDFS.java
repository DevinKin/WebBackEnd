package cn.devinkin.fastdfs;

import cn.devinkin.common.fileupload.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * @author devinkin
 * <p>Title: TestFastDFS</p>
 * <p>Description: </p>
 * @version 1.0
 * @see
 * @since 15:02 2018/9/20
 */
public class TestFastDFS {

    @Test
    public void uploadFile() throws Exception{
        // 1. 向工程中添加jar包
        // 2. 创建一个配置文件,配置tracker服务器地址
        // 3. 加载配置文件
        ClientGlobal.init("F:\\WebBackEnd\\Taotaoparent\\Taotao-manager-web\\src\\main\\resources\\client.conf");
        // 4. 创建一个TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        // 5. 使用TrackerClient对象获得TrackerServer对象.
        TrackerServer trackerServer = trackerClient.getConnection();
        // 6. 创建一个StorageServer的null引用
        StorageServer storageServer = null;
        // 7. 创建衣蛾StorageClient对象,参数: trackerServer,storageServer两个参数
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 8. 使用StorageClient对象上传文件
        String[] strings = storageClient.upload_file("F:\\WebBackEnd\\Taotaoparent\\pic\\FastDFS3.png", "png", null);
        for (String s : strings) {
            System.out.println(s);
        }
    }


    /**
     * test fileupload util
     */
    @Test
    public void testFastDFSClient() {
        FastDFSClient fastDFSClient  = null;
        try {
            fastDFSClient = new FastDFSClient("F:\\WebBackEnd\\Taotaoparent\\Taotao-manager-web\\src\\main\\resources\\client.conf");
            String s = fastDFSClient.uploadFile("F:\\WebBackEnd\\Taotaoparent\\pic\\FastDFS2.png");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
