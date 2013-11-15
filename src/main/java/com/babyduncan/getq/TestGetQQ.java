
package com.babyduncan.getq;

/**
 * Created with IntelliJ IDEA.
 * User: Guohao
 * Date: 11/15/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestGetQQ {

    public static void main(String... args) {
        String s = "<ul class=\"style5\"><li><a href=\"/profiles/1990227866\"><img src=\"http://qlogo3.store.qq.com/qzone/1990227866/1990227866/50\" alt=\"BabyDuncan2011\" />BabyDuncan2011</a></li>" +
                "<li><a href=\"/profiles/1111\"><img src=\"http://qlogo3.store.qq.com/qzone/1990227866/1990227866/50\" alt=\"BabyDuncan2011\" />BabyDuncan2011</a></li>" +
                "\n<li><a href=\"/profiles/222\"><img src=\"http://qlogo3.store.qq.com/qzone/1990227866/1990227866/50\" alt=\"BabyDuncan2011\" />BabyDuncan2011</a></li>" +
                "<li><a href=\"/profiles/333\"><img src=\"http://qlogo3.store.qq.com/qzone/1990227866/1990227866/50\" alt=\"BabyDuncan2011\" />BabyDuncan2011</a></li>                                \t\t\t\t    \t\t\t</ul>\n" +
                "\t\t\t\t    \t\t</div>";
        while (s.indexOf("\"/profiles/") > 0) {
            s = s.substring(s.indexOf("/profiles/"));
            System.out.println(s.substring(10, s.indexOf("\"><")));
            s = s.substring(s.indexOf("\"><"));
        }
    }

}
