package core;

import form.SimpleForm;

/**
 *
 * @author Develop
 */
public class Logger {

    public static void print(String str) {
        SimpleForm.jTextArea1.append(str);
    }

    public static void println(String str) {
        SimpleForm.jTextArea1.append(str+"\n");
    }

}
