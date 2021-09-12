package com.jd.edzesjd.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HTMLparse {
    public static void main(String[] args) throws IOException {
        //获取请求 https://search.jd.com/Search?keyword=java&enc=utf-8
        String url = "https://search.jd.com/Search?keyword=java&enc=utf-8";
        //解析网页
        Document document = Jsoup.parse(new URL(url), 30000);

        Element elementById = document.getElementById("J_goodsList");

        System.out.println(elementById);
    }
}
