package pl.GreczkaPiotr.javaZwirownia;

public class Main {
    public static void main(String[] args) {
        Functions.DisplayMenu("Witamy w Systemie Obsługi Żwirowni!","",true, 0, '=','|','=');

        Functions.WaitFor(750);

        GravelPitManagment.main.Initialize();

        GravelPitManagment.main.DisplayMenu("Dziękujemy za skorzystanie z programu!", "Wyłączanie aplikacji...", true);//when gets quit command
        Functions.WaitFor(1100);
        System.exit(0);
    }
}