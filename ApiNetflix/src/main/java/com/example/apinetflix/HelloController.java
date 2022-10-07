package com.example.apinetflix;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
        private MysqlxDatatypes.Scalar.String Sayhello(){
        return" welcome to the nexflix Api";
    }
}
