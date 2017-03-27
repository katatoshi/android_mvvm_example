package com.katatoshi.mvvmexample.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(Enclosed.class)
public class ListUtilTest {

    @RunWith(Enclosed.class)
    public static class replaceTest {

        public static class 置き換え先のリストのサイズが置き換え元のリストのサイズよりも小さい場合 {

            private List<String> dstList;

            private List<String> srcList;

            @Before
            public void setUp() throws Exception {
                dstList = new ArrayList<>();
                dstList.add("apple");
                dstList.add("banana");
                dstList.add("cherry");

                srcList = new ArrayList<>();
                srcList.add("Taro");
                srcList.add("Jiro");
                srcList.add("Saburo");
                srcList.add("Shiro");
                srcList.add("Goro");
            }

            @Test
            public void 実行後の置き換え先のリストのサイズは置き換え元のリストのサイズと等しい() {
                ListUtil.replace(dstList, srcList);

                assertThat(dstList, hasSize(5));
            }

            @Test
            public void 実行後の置き換え先のリストの要素は置き換え元のリストのサイズと順番も含めて等しい() {
                ListUtil.replace(dstList, srcList);

                assertThat(dstList, contains("Taro", "Jiro", "Saburo", "Shiro", "Goro"));
            }

            @Test
            public void 実行中の置き換え先のリストのsetメソッドの呼び出し回数は3回でaddメソッドの呼び出し回数は2回でremoveメソッドは一度も呼び出されない() {
                List<String> mock = mock(List.class);
                when(mock.size()).thenReturn(3);

                ListUtil.replace(mock, srcList);

                verify(mock, times(3)).set(Mockito.anyInt(), Mockito.anyString());
                verify(mock, times(2)).add(Mockito.anyString());
                verify(mock, never()).remove(Mockito.anyInt());
            }
        }

        public static class 置き換え先のリストのサイズが置き換え元のリストのサイズよりも大きい場合 {

            private List<String> dstList;

            private List<String> srcList;

            @Before
            public void setUp() throws Exception {
                dstList = new ArrayList<>();
                dstList.add("apple");
                dstList.add("banana");
                dstList.add("cherry");
                dstList.add("durian");
                dstList.add("elderberry");

                srcList = new ArrayList<>();
                srcList.add("Taro");
                srcList.add("Jiro");
                srcList.add("Saburo");
            }

            @Test
            public void 実行後の置き換え先のリストのサイズは置き換え元のリストのサイズと等しい() {
                ListUtil.replace(dstList, srcList);

                assertThat(dstList, hasSize(3));
            }

            @Test
            public void 実行後の置き換え先のリストの要素は置き換え元のリストのサイズと順番も含めて等しい() {
                ListUtil.replace(dstList, srcList);

                assertThat(dstList, contains("Taro", "Jiro", "Saburo"));
            }

            @Test
            public void 実行中の置き換え先のリストのsetメソッドの呼び出し回数は3回でremoveメソッドの呼び出し回数は2回でaddメソッドは一度も呼び出されない() {
                List<String> mock = mock(List.class);
                when(mock.size()).thenReturn(5);

                ListUtil.replace(mock, srcList);

                verify(mock, times(3)).set(Mockito.anyInt(), Mockito.anyString());
                verify(mock, times(2)).remove(Mockito.anyInt());
                verify(mock, never()).add(Mockito.anyString());
            }
        }

        public static class 置き換え先のリストのサイズが置き換え元のリストのサイズよりと等しい場合 {

            private List<String> dstList;

            private List<String> srcList;

            @Before
            public void setUp() throws Exception {
                dstList = new ArrayList<>();
                dstList.add("apple");
                dstList.add("banana");
                dstList.add("cherry");
                dstList.add("durian");
                dstList.add("elderberry");

                srcList = new ArrayList<>();
                srcList.add("Taro");
                srcList.add("Jiro");
                srcList.add("Saburo");
                srcList.add("Shiro");
                srcList.add("Goro");
            }

            @Test
            public void 実行後の置き換え先のリストのサイズは置き換え元のリストのサイズと等しい() {
                ListUtil.replace(dstList, srcList);

                assertThat(dstList, hasSize(5));
            }

            @Test
            public void 実行後の置き換え先のリストの要素は置き換え元のリストのサイズと順番も含めて等しい() {
                ListUtil.replace(dstList, srcList);

                assertThat(dstList, contains("Taro", "Jiro", "Saburo", "Shiro", "Goro"));
            }

            @Test
            public void 実行中の置き換え先のリストのsetメソッドの呼び出し回数は5回でaddメソッドとremoveメソッドは一度も呼び出されない() {
                List<String> mock = mock(List.class);
                when(mock.size()).thenReturn(5);

                ListUtil.replace(mock, srcList);
                
                verify(mock, times(5)).set(Mockito.anyInt(), Mockito.anyString());
                verify(mock, never()).add(Mockito.anyString());
                verify(mock, never()).remove(Mockito.anyInt());
            }
        }
    }
}