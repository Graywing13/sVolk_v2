package ui;

import util.ProcessTxt;

public class Main {
    public UI ui;

    public Main() {
        System.out.println("sVolk_v2 started.");
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.gameInit();
    }

    public void gameInit() {
        System.out.println("♥ ♥ ♥ Setting Up Dictionaries ♥ ♥ ♥");
        ProcessTxt.initDictionary(ProcessTxt.ABILITIES_DICT_LOCATION, "\\w+(\\s-)?(\\s+\\w+)*((,\\w+(-\\d+)?,\\d+){2}){2}");
        ProcessTxt.initDictionary(ProcessTxt.CHAR_INFO_DICT_LOCATION, "\\w+(\\s\\w+)*,\\w+,\\w+(,\\w+(\\s-)?(\\s\\w+)*){7}(,\\d+){4},\\w+(\\s\\w+)*");
        ProcessTxt.initDictionary(ProcessTxt.COABS_DICT_LOCATION, "\\w+(\\s-)?(\\s\\w+)*,\\w+(-\\d+(\\.\\d+)?)*,\\d+(\\.\\d+)?,(true|false)");
        ProcessTxt.initDictionary(ProcessTxt.SKILLS_DICT_LOCATION, "\\w+(\\s\\w+)*,\\d+,\\d+(\\.\\d+)?(,(true|false)){2}(,\\d+){4}(,\\w+(-\\d+(\\.\\d+)?)*,\\d+(\\.\\d+)?){3}");
        ProcessTxt.initDictionary(ProcessTxt.WEAPONS_DICT_LOCATION, "\\w+(\\s\\w+)*(,\\w+){2}(,\\d+){4},\\w+(\\s-)?(\\s\\w+)*");

        ui = new UI();
    }

}
