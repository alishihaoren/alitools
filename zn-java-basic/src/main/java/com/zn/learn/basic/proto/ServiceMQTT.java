package com.zn.learn.basic.proto;



import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Scanner;

public class ServiceMQTT {

    public static final String HOST = "tcp://10.162.201.71:1883";
    private String ServiceID = "ServiceFirst";
    private String topic;
    private MqttClient client;
    private MqttTopic mqttTopic;
    private MqttConnectOptions options;
    private String user = "admin";
    private String password = "public";

    private MqttMessage message;

    public ServiceMQTT() throws MqttException {
        //创建连接
        client = new MqttClient(HOST,ServiceID,new MemoryPersistence());
        options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setKeepAliveInterval(20);
        options.setConnectionTimeout(50);
        options.setUserName(user);
        options.setPassword(password.toCharArray());
        message = new MqttMessage();
    }

    public void getConnect(){
        try {
            client.setCallback(new PublishCallBack());
            client.connect(options);
            mqttTopic = client.getTopic(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(MqttTopic topic, MqttMessage message) throws MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("消息推送的状态--->"+token.isComplete());
    }


    public static void main(String[] args) throws MqttException {
        ServiceMQTT service = new ServiceMQTT();
        Scanner input = new Scanner(System.in);

        System.out.print("请输入消息的主题：");
        service.topic = input.next();

        System.out.print("请输入消息的内容：");
        String messageVal = input.next();

        service.getConnect();

        service.message.setQos(1);
        service.message.setRetained(true);
        service.message.setPayload(messageVal.getBytes());

        service.publish(service.mqttTopic,service.message);
        System.out.println("消息的保持状态："+service.message.isRetained());
    }
}

