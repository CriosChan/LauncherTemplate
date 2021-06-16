package fr.crioschan.bootstrap;

import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ClasspathConstructor;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.openlauncherlib.util.SplashScreen;
import fr.theshark34.openlauncherlib.util.explorer.ExploredDirectory;
import fr.theshark34.openlauncherlib.util.explorer.Explorer;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;

import java.io.File;
import java.util.Arrays;

import static fr.theshark34.swinger.Swinger.getTransparentWhite;

public class AlfheimBootstrap {

private static final File N_DIR = GameDirGenerator.createGameDir("SERVER NAME");
    public static CrashReporter errorUtil = new CrashReporter("SERVER NAME", new File(N_DIR, "crashes"));

    private static SplashScreen splash;
    private static Thread barThread;

    public static void main(String[] args){
        Swinger.setResourcePath("/fr/crioschan/bootstrap/ressources");
        displaySplash();
        try{
            doUpdate();
        } catch (Exception e){
            errorUtil.catchError(e, "Unable to update the launcher");
            barThread.interrupt();
        }
    }

    private static void displaySplash (){
        splash = new SplashScreen("SERVER NAME", Swinger.getResource("splashv2.png"));

        splash.setVisible(true);
    }

    private static void doUpdate() throws Exception{
        SUpdate su = new SUpdate("SUPDATE SERVER URL", new File(N_DIR, "Launcher"));

        su.start();

        AlfheimBootstrap.launch();
    }

    static void launch() throws LaunchException {
        ClasspathConstructor constructor = new ClasspathConstructor();
        ExploredDirectory gameDir = Explorer.dir(N_DIR);
        constructor.add(gameDir.sub("Launcher/libs").allRecursive().files().match("^(.*\\.((jar)$))*$"));
        constructor.add(gameDir.get("Launcher/launcher.jar"));

        ExternalLaunchProfile profile = new ExternalLaunchProfile("fr.crioschan.template.launcher.LauncherFrame", constructor.make());
        ExternalLauncher launcher = new ExternalLauncher(profile);

        Process p = launcher.launch();
        System.exit(0);

        try {
            p.waitFor();
        } catch (InterruptedException ignored){
        }
    }

}
