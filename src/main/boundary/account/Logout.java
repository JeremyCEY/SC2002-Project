/*
 * @Author: tyt1130 tangyutong0306@gmail.com
 * @Date: 2023-11-09 11:03:36
 * @LastEditors: tyt1130 tangyutong0306@gmail.com
 * @LastEditTime: 2023-11-26 13:21:49
 * @FilePath: \SC2002-Project\src\main\boundary\account\Logout.java
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
/**
 * The main.boundary.account package contains classes responsible for providing user interfaces
 * related to user account actions, including logout functionality.
 */
package main.boundary.account;

import main.boundary.welcome.Welcome;

/**
 * The Logout class provides a user interface (UI) for the user to logout.
 */
public class Logout {
    /**
     * Displays a logout page and terminates the application.
     */
    public static void logout() {
        // Display the welcome page
        Welcome.welcome();
        
        // Terminate the application
        System.exit(0);
    }
}

