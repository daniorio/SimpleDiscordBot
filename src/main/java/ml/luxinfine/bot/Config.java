package ml.luxinfine.bot;

import org.javacord.api.entity.activity.ActivityType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Config {
    //*     BOOSTING     *//
    public static class boosts {
        public static String role_id = "7735164499157";
    }

    //*     BOT     *//
    public static class bot {
        public static String token = "Nj2deXEsM";
        public static String server_id = "48834354261268";
        public static ActivityType activity = ActivityType.PLAYING;
    }

    //*     DATABASE     *//
    public static class database {
        public static String url = "jdbc:mysql://localhost:3306/db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        public static String user = "user";
        public static String password = "password";
        public static String users_table = "dle_users";
        public static String username_column = "name";
        public static String balance_column = "balance";
        public static String discord_column = "discord";
        public static String uuid_column = "uuid";
    }

    //*     COMMANDS     *//
    public static class command {
        public static boolean enabled = true;
        public static boolean send_unknown_command_exception = true;
        public static String command_prefix = "!";
        public static String channel = "695661577728";

        public static class common {

            public static class unlink {
                public static boolean enabled = true;
                public static List<String> aliases = Arrays.asList("!unlink", "!unconnect", "!отвязать", "!отвязка");
                public static List<String> roles = Collections.singletonList("*");
            }

            public static class balance {
                public static boolean enabled = true;
                public static List<String> aliases = Arrays.asList("!balance", "!money", "!баланс", "!деньги", "!валюта", "!бюджет", "!luxes", "!люксы");
                public static List<String> roles = Arrays.asList("*");
                public static String icon_path = "icons/money.png";

                public static class standard {
                    public static boolean enabled = true;
                    public static List<String> roles = Arrays.asList("*");
                }

                public static class other {
                    public static boolean enabled = true;
                    public static List<String> roles = Arrays.asList("*");
                }

                public static class top {
                    public static boolean enabled = true;
                    public static List<String> aliases = Arrays.asList("top", "топ");
                    public static List<String> roles = Arrays.asList("*");
                    public static int size = 10;
                    public static String icon_path = "icons/top.png";
                }

                public static class set {
                    public static boolean enabled = true;
                    public static List<String> aliases = Arrays.asList("set", "установить", "поставить");
                    public static List<String> roles = Arrays.asList("488341739392729088");
                }

                public static class add {
                    public static boolean enabled = true;
                    public static List<String> aliases = Arrays.asList("add", "добавить", "прибавить");
                    public static List<String> roles = Arrays.asList("488341739392729088");
                }

                public static class remove {
                    public static boolean enabled = true;
                    public static List<String> aliases = Arrays.asList("remove", "забрать", "убрать");
                    public static List<String> roles = Arrays.asList("488341739392729088");
                }
            }
        }

        public static class pm {
            public static class link {
                public static boolean enabled = true;
                public static boolean update_name = true;
                public static List<String> aliases = Arrays.asList("!link", "!connect", "!привязать", "!привязка");
                public static List<String> roles = Arrays.asList("*");
                public static String link_url = "http://localhost/api/auth.php";
            }

            public static class register {
                public static boolean enabled = true;
                public static List<String> aliases = Arrays.asList("!reg", "!register", "!регистрация", "!registration");
                public static List<String> roles = Arrays.asList("*");
                public static int min_password_length = 6;
                public static int cooldown = 60 * 60 * 1000; //миллисекунды
                public static String link_url = "http://localhost/api/register.php";
            }
        }
    }

    //*     COOLDOWN     *//
    public static class cooldown {
        public static class command {
            public static boolean enabled = true;
            public static List<String> invulnerability_roles = Arrays.asList("865214791136051240", "865216038047055874", "865216631440932874");
            public static int seconds = 3;
        }
    }

    //*     LOGGING     *//
    public static class logging {
        public static boolean enabled = true;
        public static String channel = "6465474063817226";
    }

    //*     CACHE     *//
    public static class cache {
        public static boolean enabled = false;
        public static int size = 100;
        public static int seconds = 6 * 60 * 60;
    }

    //*     ACTIVITY     *//
    public static class activity {
        public static boolean enabled = true;
        public static double booster_multiplier = 1.25;
        public static class write {
            public static boolean enabled = true;
            public static double min_reward = 0.01;
            public static double max_reward = 0.075;
            public static int cooldown = 25;
            public static int chance = 3;
        }
        public static class edit {
            public static boolean enabled = true;
            public static double min_reward = 0.01;
            public static double max_reward = 0.03;
            public static int cooldown = 40;
            public static int chance = 5;
        }
        public static class voice {
            public static boolean enabled = true;
            public static double min_reward = 0.05;
            public static double max_reward = 0.1;
            public static int cooldown = 300;
            public static int chance = 2;
        }
        public static class reaction {
            public static boolean enabled = true;
            public static double min_reward = 0.01;
            public static double max_reward = 0.05;
            public static int cooldown = 45;
            public static int chance = 3;
        }
    }
}
