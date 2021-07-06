package com.epam.messaging.firstapp;

import com.epam.jmp.messaging.domain.GoodsType;
import com.epam.jmp.messaging.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import java.util.Scanner;

@SpringBootApplication
@EnableJms
public class FirstApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FirstApp.class);
    }

    @Autowired
    OrderMessageProducer messageProducer;

    @Override
    public void run(String... args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Fill order details");
            System.out.println("Order id:");
            int id = scanner.nextInt();
            System.out.println("User name:");
            String userName = scanner.next();
            System.out.println("Type goods (1 - countable, 2 - liquid):");
            GoodsType goodsType = (scanner.nextInt() == 1) ? GoodsType.COUNTABLE : GoodsType.LIQUID;
            double volume = 1.0;
            if (goodsType == GoodsType.LIQUID) {
                System.out.println("Volume of liquid:");
                volume = scanner.nextDouble();
            }
            System.out.println("Items number:");
            int itemsNumber = scanner.nextInt();
            System.out.println("Total price:");
            double total = scanner.nextDouble();
            Order order = Order.builder().id(id).user(userName).goodsType(goodsType)
                    .volume(volume).itemsNumber(itemsNumber).total(total).build();
            messageProducer.sendOrderMessage(order);
            System.out.println("Order is sent");
        }
    }
}
