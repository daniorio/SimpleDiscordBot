package ml.luxinfine.bot;

public class Messages {

    //*     BOT     *//
    public static class bot {
        public static String GAME_STATUS = "https://luxinfine.su/";
    }

    //*     COMMANDS     *//
    public static class command {
        public static String DISABLED = "Ошибка при выполнении команды:Данная команда отключена";
        public static String COOLDOWN = "Ошибка при выполнении команды:Подождите немного, прежде чем использовать команду снова";
        public static String UNKNOWN = "Неизвестная команда:Для получения списка команд введите **!help**";
        public static String USER_NOT_FOUND = "Ошибка при выполнении команды:Указанный пользователь не найден";
        public static String UNEXPECTED_ERROR = "Ошибка при выполнении команды:Произошла непредвиденная ошибка, попробуйте выполнить это действие позже";
        public static String NO_PERMISSION = "Ошибка при выполнении команды:У вас недостаточно прав для выполнения этой команды!";
        public static String USAGE = "Ошибка при выполнении команды:Используйте команду **%s**";

        //*     PRIVATE COMMANDS     *//
        public static class pm {
            public static String INSTRUCTION_GET = "Ошибка при выполнении команды:Инструкция по выполнению команды отправлена вам в личные сообщения";
            public static String BAD_CHANNEL = "Нарушение безопасности:Вы только что ввели свой логин и пароль в общедоступном канале. Привязка аккаунта происходит только в личных сообщения боту. Будьте внимательны!";
            public static String BAD_FORMAT = "Ошибка при выполнении команды:Ваши логин, пароль или почту не нужно указывать в <>. Эти скобку нужны, чтобы обозначать обязательный аргумент.";

            public static class register {
                public static String USAGE = "!register <ник> <пароль> <почта>";
                public static String INSTRUCTION = "Регистрация:Для регистрации на проекте используйте команду **!register <ник> <пароль> <почта>**";
                public static String COOLDOWN = "Ошибка при выполнении команды:Прежде чем регистрировать аккаунт снова, подождите некоторое время";
                public static String SUCCESS = "Регистрация:Вы успешно зарегистрировались и можете начинать играть! Если хотите быть подтвержденным пользователем, то должны следовать инструкции на указанной почте. Подтверждение доступно только в течение суток после регистрации.";
                public static String ALREADY_CONNECTED = "Ошибка при выполнении команды:Ваш аккаунт в Discord уже привязан к аккаунту майнкрафт";
                public static String BAD_EMAIL = "Ошибка при выполнении команды:Вы ввели некорректный адрес почты или данная почта уже используется";
                public static String BAD_NICKNAME = "Ошибка при выполнении команды:Вы ввели некорректный ник или он уже занят";
                public static String SHORT_PASSWORD = "Ошибка при выполнении команды:Минимально допустимая длина пароля - %s символов";
            }

            public static class link {
                public static String USAGE = "!link <ник> <пароль>";
                public static String INSTRUCTION = "Привязка аккаунта:Для привязки аккаунта используйте команду **!link <ник> <пароль>**";
                public static String SUCCESS = "Привязка аккаунта:Вы связали ваш аккаунт майнкрафт с аккаунтом в Discord. В целях безопасности не забудьте удалить ваши сообщения, содержащие логин и пароль.";
                public static String BAD_LOGIN_OR_PASSWORD = "Ошибка при выполнении команды:Вы ввели неправильный логин или пароль";
                public static String ALREADY_CONNECTED = "Ошибка при выполнении команды:Ваш аккаунт в Discord уже привязан к аккаунту майнкрафт";
            }
        }

        //*     PUBLIC COMMANDS     *//
        public static class common {
            public static class balance {
                public static String USAGE = "!balance @user | !balance top | !balance set/add/remove @user <сумма>";
                public static String TITLE = "Информация о балансе";
                public static String DESCRIPTION = "Баланс пользователя %s - %s люксов";

                public static class top {
                    public static String TITLE = "Топ богатых игроков";
                    public static String LINE = "%s. %s - %s люксов\n";
                }

                public static class set {
                    public static String SUCCESS = "Изменение баланса пользователя:Вы успешно установили баланс в %s люксов пользователю %s";
                    public static String TO_USER = "Изменение баланса:Ваш текущий баланс был установлен на %s люксов";
                }

                public static class add {
                    public static String SUCCESS = "Изменение баланса пользователя:Вы успешно добавили на баланс %s люксов пользователю %s";
                    public static String TO_USER = "Изменение баланса:На ваш баланс было добавлено %s люксов";
                }

                public static class remove {
                    public static String SUCCESS = "Изменение баланса пользователя:Вы успешно сняли с баланса %s люксов у пользователя %s";
                    public static String TO_USER = "Изменение баланса:С вашего баланса было снято %s люксов";
                }
            }

            public static class unlink {
                public static String USAGE = "!unlink";
                public static String SUCCESS = "Отвязка аккаунта:Ваш аккаунт в дискорд был успешно отвязан от аккаунта в майнкрафт с ником %s";
            }
        }
    }

    //*     ACCOUNT     *//
    public static class account {
        public static String NOT_CONNECT_OWN = "Аккаунт не привязан:Ваш аккаунт в майнкрафт не привязан к аккаунту Discord. Для привязки отправьте в личные сообщения боту команду **!link**. Если у вас еще нет аккаунта на нашем проекте, вы можете зарегистрировать его с помощью команды **!register**.";
    }

    //*     LOGGING     *//
    public static class logging {
        public static String REGISTER = ":heavy_check_mark: Пользователь **%s** успешно прошел первый шаг регистрации с ником **%s**, почтой **%s** и паролем **%s**";
        public static String LINK = ":heavy_check_mark: Пользователь **%s** успешно привязал свой аккаунт с ником **%s** к Discord";
        public static String UNLINK = ":heavy_check_mark: Пользователь **%s** успешно отвязал свой аккаунт с ником **%s** от Discord";
        public static String ACTIVITY_MSG = ":heavy_plus_sign: Пользователь **%s** получил **%s** люксов за активность (сообщения)";
        public static String ACTIVITY_EDIT = ":heavy_plus_sign: Пользователь %s получил **%s** люксов за активность (редактирование сообщений)";
        public static String ACTIVITY_EMOJI = ":heavy_plus_sign: Пользователь **%s** получил **%s** люксов за активность (реакции)";
        public static String ACTIVITY_VOICE = ":heavy_plus_sign: Пользователь **%s** получил **%s** люксов за активность (голос)";
        public static String BALANCE_ADD = ":heavy_plus_sign: Пользователь **%s** начислил пользователю **%s** %s люксов";
        public static String BALANCE_REMOVE = ":heavy_minus_sign: Пользователь **%s** снял у пользователя **%s** %s люксов";
        public static String BALANCE_SET = ":heavy_dollar_sign: Пользователь **%s** установил баланс у пользователя **%s** в %s люксов";
    }
}
