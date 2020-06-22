// Notes:
//   - consider moving some of these dictionaries to their respective classes
//   - consider adding a Playable class from which Char and Enemy extend.

package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ProcessTxt {

    // General Constants
    public final static List<String> ELEMENTS = Arrays.asList("Flame", "Light", "None", "Shadow", "Water", "Wind"); // note: none type is for unattuned weapons
    public final static ArrayList<String> FWW_ELEM_CYCLE = new ArrayList<>(Arrays.asList("Flame", "Wind", "Water"));
    public final static ArrayList<String> LS_ELEM_CYCLE = new ArrayList<>(Arrays.asList("Light", "Shadow"));


    // Dictionary Constants
    private final static String DICT_LOCATION = "./data/dict/";
    public final static String ABILITIES_DICT_LOCATION = DICT_LOCATION + "abilities.txt";
    public static final HashMap<String, Ability> ABILITIES_DICTIONARY = new HashMap<>();
    public final static String CHAR_INFO_DICT_LOCATION = DICT_LOCATION + "char_info.txt";
    public static final HashMap<String, Char> CHAR_INFO_DICTIONARY = new HashMap<>();
    public final static String COABS_DICT_LOCATION = DICT_LOCATION + "coabs.txt";
    public static final HashMap<String, Coab> COABS_DICTIONARY = new HashMap<>();
    //public final static String PRINTS_DICT_LOCATION = DICT_LOCATION + "prints.txt";
    //public static final HashMap<String, Print> PRINTS_DICTIONARY = new HashMap<>();
    public final static String SKILLS_DICT_LOCATION = DICT_LOCATION + "skills.txt";
    public static final HashMap<String, Skill> SKILLS_DICTIONARY = new HashMap<>();
    public final static String WEAPONS_DICT_LOCATION = DICT_LOCATION + "weapons.txt";
    public static final HashMap<String, Weapon> WEAPONS_DICTIONARY = new HashMap<>();
    public static final String WEAPON_TYPES_DICT_LOCATION = DICT_LOCATION + "weapontypes.txt";
    public static final HashMap<String, WeaponType> WEAPON_TYPES_DICTIONARY = new HashMap<>();

    // ♦ ♦ ♦ Dictionary Creation (Main)
    public static void initDictionary(String fileLocation, String regexMatch) {
        try {
            File file = new File(fileLocation);
            Scanner scanner = new Scanner(file).useDelimiter("\n");
            int numEntries = 0;
            while (scanner.hasNextLine()) {
                String string = scanner.nextLine().trim();
                if (string.startsWith("//")) continue;
                numEntries++;
                System.out.print("Processing entry number " + numEntries + "\r");
                switch (fileLocation) {
                    case ABILITIES_DICT_LOCATION:
                        if (!string.matches(regexMatch)) throw new RuntimeException("Invalid string: " + string);
                        addLineToAbilDict(fileLocation, string);
                        break;
                    case CHAR_INFO_DICT_LOCATION:
                        if (!string.matches(regexMatch)) throw new RuntimeException("Invalid string: " + string);
                        addLineToCharDict(fileLocation, string);
                        break;
                    case COABS_DICT_LOCATION:
                        if (!string.matches(regexMatch)) throw new RuntimeException("Invalid string: " + string);
                        addLineToCoabDict(fileLocation, string);
                        break;
                    case SKILLS_DICT_LOCATION:
                        if (!string.matches(regexMatch)) throw new RuntimeException("Invalid string: " + string);
                        addLineToSkillsDict(fileLocation, string);
                        break;
                    case WEAPONS_DICT_LOCATION:
                        if (!string.matches(regexMatch)) throw new RuntimeException("Invalid string: " + string);
                        addLineToWeaponDict(fileLocation, string);
                        break;
                    case WEAPON_TYPES_DICT_LOCATION:
                        if (!string.matches(regexMatch)) throw new RuntimeException("Invalid string: " + string);
                        addLineToWeaponTypeDict(fileLocation, string);
                        break;
                    default:
                        throw new RuntimeException("Unrecognized dictionary location " + fileLocation);
                }
                System.out.flush();
            }
            scanner.close();
            System.out.println("Successfully added " + numEntries + " dictionary entries to '" + fileLocation + "'!");


        } catch (FileNotFoundException e) {
            // do nothing
        }
    }

    // ♦ ♦ ♦ Adding entries to dictionaries
    private static void addLineToAbilDict(String fileLocation, String entry) {
        String name, a1, a1R, a2, a2R;
        int a1P, a1RC, a2P, a2RC;
        Scanner parseLine = new Scanner(entry).useDelimiter(",");
        try {
            name = parseLine.next();
            a1 = parseLine.next();
            a1P = parseLine.nextInt();
            a1R = parseLine.next();
            a1RC = parseLine.nextInt();
            a2 = parseLine.next();
            a2P = parseLine.nextInt();
            a2R = parseLine.next();
            a2RC = parseLine.nextInt();
            noDuplicates(ProcessTxt.ABILITIES_DICTIONARY, name, entry, fileLocation);
            ProcessTxt.ABILITIES_DICTIONARY.put(name, new Ability(name, a1, a1P, a1R, a1RC, a2, a2P, a2R, a2RC));
            parseLine.close();
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid string: " + entry);
        }
    }

    private static void addLineToCharDict(String fileLocation, String entry) {
        // String name, elem, wT, s1N, s2N, a1, a2, a3, ct, cc;
        //int mt, hp, str, def;
        // Weapon w;
        Scanner parseLine = new Scanner(entry).useDelimiter(",");
        try {
            String[] s = new String[10];
            for (int i = 0; i < 10; i++) {
                s[i] = parseLine.next();
            }
            int[] i = new int[4];
            for (int j = 0; j < 4; j++) {
                i[j] = parseLine.nextInt();
            }
            validElem(s[1]);
            validWeaponType(s[2]);
            noDuplicates(CHAR_INFO_DICTIONARY, s[0], entry, fileLocation);
            CHAR_INFO_DICTIONARY.put(s[0], new Char(s[0], s[1], s[2], s[3], s[4], s[5], s[6], s[7], s[8], s[9], i[0], i[1], i[2], i[3], null));
            parseLine.close();
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid string: " + entry);
        }
    }

    private static void addLineToCoabDict(String fileLocation, String entry) {
        String skillName, coabAbil;
        double coabPercentage;
        boolean wholeTeam;

        Scanner parseLine = new Scanner(entry).useDelimiter(",");
        try {
            skillName = parseLine.next();
            coabAbil = parseLine.next();
            coabPercentage = parseLine.nextDouble();
            wholeTeam = parseLine.nextBoolean();
            noDuplicates(COABS_DICTIONARY, skillName, entry, fileLocation);
            COABS_DICTIONARY.put(skillName, new Coab(skillName, coabAbil, coabPercentage, wholeTeam));
            parseLine.close();
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid string: " + entry);
        }
    }

    private static void addLineToSkillsDict(String fileLocation, String entry) {
        String sN, b1N, b2N, b3N;
        int spC, d1P, d1H, d2P, d2H;
        double iFT, b1P, b2P, b3P;
        boolean cM, eA;
        Scanner parseLine = new Scanner(entry).useDelimiter(",");
        try {
            sN = parseLine.next();
            spC = parseLine.nextInt();
            iFT = parseLine.nextDouble();
            cM = parseLine.nextBoolean();
            eA = parseLine.nextBoolean();
            d1P = parseLine.nextInt();
            d1H = parseLine.nextInt();
            d2P = parseLine.nextInt();
            d2H = parseLine.nextInt();
            b1N = parseLine.next();
            b1P = parseLine.nextDouble();
            b2N = parseLine.next();
            b2P = parseLine.nextDouble();
            b3N = parseLine.next();
            b3P = parseLine.nextDouble();

            noDuplicates(SKILLS_DICTIONARY, sN, entry, fileLocation);
            SKILLS_DICTIONARY.put(sN, new Skill(sN, spC, iFT, cM, eA, d1P, d1H, d2P, d2H, b1N, b1P, b2N, b2P, b3N, b3P));
            parseLine.close();
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid string: " + entry);
        }
    }

    private static void addLineToWeaponDict(String fileLocation, String entry) {

        assert(!WEAPON_TYPES_DICTIONARY.isEmpty());

        // String wN, elem, wT;
        // int mtE, mtOE, hp, str;
        String aN;

        Scanner parseLine = new Scanner(entry).useDelimiter(",");
        try {
            String[] s = new String[3];
            for (int i = 0; i < 3; i++) {
                s[i] = parseLine.next();
            }
            int[] i = new int[4];
            for (int j = 0; j < 4; j++) {
                i[j] = parseLine.nextInt();
            }
            aN = parseLine.next();

            validWeaponType(s[2]);
            validElem(s[1]);
            noDuplicates(WEAPONS_DICTIONARY, s[0], entry, fileLocation);

            WEAPONS_DICTIONARY.put(s[0], new Weapon(s[0], s[1], s[2], i[0], i[1], i[2], i[3], aN));
            parseLine.close();
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid string: " + entry);
        }
    }

    private static void addLineToWeaponTypeDict(String fileLocation, String entry) {
        String wT;
        double wR;

        Scanner parseLine = new Scanner(entry).useDelimiter(",");
        try {
            wT = parseLine.next();
            wR = parseLine.nextDouble();

            noDuplicates(WEAPON_TYPES_DICTIONARY, wT, entry, fileLocation);

            WEAPON_TYPES_DICTIONARY.put(wT, new WeaponType(wT, wR));
            parseLine.close();
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid string: " + entry);
        }
    }

    // ♦ ♦ ♦ Small Helper Functions
    public static String stringSplit(String toSplit, String delimiter, int location) {
        // NOTE: location of 0 means "take string BEFORE the delimiter", 1 means AFTER. All other positive ints are etc.
        return toSplit.split(delimiter)[location];
    }

    private static void validElem(String elem) {
        if (!ELEMENTS.contains(elem)) throw new RuntimeException("The element " + elem + " is invalid.");
    }

    private static void validWeaponType(String wT) {
        if (!WEAPON_TYPES_DICTIONARY.containsKey(wT)) throw new RuntimeException("The weapon type " + wT + " is invalid.");
    }

    private static void noDuplicates(HashMap<String, ?> dictionary, String key, String entry, String fileLocation) {
        if (dictionary.containsKey(key))
            throw new RuntimeException("The entry " + entry + " already exists in " + fileLocation);
    }

    public static String[] findAvailChars() {
        ArrayList<String> availCharNames = new ArrayList<>(CHAR_INFO_DICTIONARY.keySet());
        String[] arr = availCharNames.toArray(new String[0]);
        Arrays.sort(arr);
        return arr;
    }

}
