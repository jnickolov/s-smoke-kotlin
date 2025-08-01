package eu.efficientsoft.lpl.ssmoke.mobileapp.screens

import kotlinx.serialization.Serializable

@Serializable
class I18n (val code: String, val props: Map<String, String>) {
    fun prop (key: String) = props[key] ?: key
}

val i18nBg_preloaded: I18n = I18n (code="bg", props = mapOf(
    "m_mnu_fire_alarms" to "Пожарни аларми",
    "m_acc_info" to "Информация",
    "m_alarm" to "\"Събитие\"",
    "m_available_unm" to "Потребителското име може да се използва",
    "m_browse" to "Разгледай",
    "m_cancel" to "Отказ",
    "m_chk" to "Провери",
    "m_chng_info" to "Редактирай",
    "m_chng_pwd" to "Смяна на паролата",
    "m_datetime" to "\"Дата и час\"",
    "m_disclaimer" to "Въведените данни няма да се използват за други цели",
    "m_email" to "E-mail",
    "m_email_descr" to "незадължителна, но някои функции изискват истински имейл",
    "m_end_date" to "\"Крайна дата\"",
    "m_err" to "Грешка",
    "m_err_server" to "Грешка при свързването със сървъра",
    "m_fcm" to "Получаване на съобщения",
    "m_fgt_pwd" to "Забравена парола",
    "m_have_acc" to "Вече имам регистрация",
    "m_home" to "Начало",
    "m_info" to "Инфо",
    "m_lang" to "Език",
    "m_last_login" to "Последно влизане",
    "m_login" to "Вход",
    "m_login_and_repeat" to "Моля влезте в системата и повторете операцията",
    "m_login_required" to "Моля влезте в системата",
    "m_login_title" to "Здравейте",
    "m_logout" to "Изход",
    "m_mnu_config" to "Настройки",
    "m_mnu_config_hint" to "Предпочитани насторйки на приложрнието",
    "m_mnu_detector" to "Детектори",
    "m_mnu_detector_hint" to "Присъединяване и управление на детекторите",
    "m_mnu_fcm" to "Съобщения",
    "m_mnu_fcm_hint" to "Управление на съобщенията от детекторите и системата",
    "m_mnu_help" to "Помощ",
    "m_mnu_help_hint" to "Пълно описание. За описание на конкретна тема, използвайте бутона в десния горен ъгъл",
    "m_mnu_logout" to "Изход",
    "m_mnu_logout_hint" to "Излизане от профила\\nЩе бъдете насочени към екрана за свързване",
    "m_mnu_reports" to "Справки",
    "m_mnu_reports_hint" to "Информация за събития в усторйствата и системата с възможност за филтрация",
    "m_mnu_user" to "Профил",
    "m_mnu_user_hint" to "Управление на персоналните данни",
    "m_new_pwd" to "Нова парола",
    "m_new_user" to "Създай нов потребител",
    "m_nm" to "Име",
    "m_nm_descr" to "реално име, незадължително",
    "m_no_acc" to "Нямам регистрация",
    "m_no_data_in_period" to "Няма събития в избрания период",
    "m_none" to "Няма",
    "m_ok" to "ОК",
    "m_pass" to "Парола",
    "m_pass2" to "Потвърдете паролата",
    "m_pass2_descr" to "повторете паролата",
    "m_pass_descr" to "поне 8 символа",
    "m_period" to "Период",
    "m_reg" to "Регистрирай",
    "m_remove_fcm" to "Спиране на всички съобщения",
    "m_remove_fcm_info" to "Потвърдете отказ от получаване на всякакви съобщения на това устройство. По-късно можете да включите получаването отоново.",
    "m_save" to "Запази",
    "m_select_period" to "Изберете период",
    "m_start_date" to "Начална дата",
    "m_submit_report" to "Справка",
    "m_unm" to "Потребителско име",
    "m_unm_descr" to "трябва да бъде уникално, поне 6 букви",
    "m_ver" to "Версия",
    "m_wrong_credentials" to "Неуспешно влизане, опитайте пак",
    "m_wrong_pass" to "Паролата трябва да е поне 6 символа",
    "m_wrong_unm" to "Името трябва да е поне 6 символа (латински букви)")
)


enum class SSmokeLangKey {
    BG,
    EN;

    // toString is the same:
//    fun asString() {
//        when (this) {
//            EN -> "EN"
//            BG -> "BG"
//            else -> "UNKNOWN"
//        }
//    }
}