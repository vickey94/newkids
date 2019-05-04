package gxnu.newkids.service;

import gxnu.newkids.entity.Wordbook;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class WordbookServiceTest {

    @Resource
    WordbookService wbservice;

    @Test
    public void getAllWb() {
       Map map = wbservice.getAllWb("1385974ed5904a438616ff7b");

        System.out.println();
    }
}