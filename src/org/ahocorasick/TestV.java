package org.ahocorasick;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.apache.commons.io.FileUtils;



public class TestV {
	public static String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        if(str != null) {
            //用源字符编码解码字符串
            byte[] bs = str.getBytes(oldCharset);
            return new String(bs, newCharset);
        }
        return null;
    }
public static void main(String[] args) throws IOException {
  
	Trie trie = new Trie(false);
	trie.setPool(true);
	trie.addKeyword("ab");
	trie.addKeyword("b");
	trie.addKeyword("c");
	trie.addKeyword("d");
	Collection<Emit> sd = trie.parseText("ajkhkhjhjabsdjsk");
	System.out.println(sd);
	
	
//
//  
//  System.out.println("---------------2-------------");
//	
	


}
}
