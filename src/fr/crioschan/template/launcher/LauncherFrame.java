package fr.crioschan.template.launcher;

import com.sun.awt.AWTUtilities;
import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;

public class LauncherFrame extends JFrame {

    private static LauncherFrame instance;
    private static LauncherPanel LauncherPanel;
    private static shaderPanel shaderPanel;
    private static CrashReporter crashReporter;

    public LauncherFrame() {
        this.setTitle("SERVER NAME");
        this.setSize(1200, 675);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setIconImage(Swinger.getResource("icon.png"));
        this.setContentPane(LauncherPanel = new LauncherPanel());
        AWTUtilities.setWindowOpacity(this, 0.0F);

        WindowMover mover = new WindowMover(this);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);

        this.setVisible(true);

        Animator.fadeInFrame(this, Animator.FAST);

    }

    //Start launcher
    public static void main(String[] srgs) {
        Crypto.Decrypt();
        Swinger.setSystemLookNFeel();
        Swinger.setResourcePath("/fr/crioschan/template/launcher/ressource");
        Launcher.N_CRASHES_DIR.mkdirs();
        crashReporter = new CrashReporter("SERVER NAME", Launcher.N_CRASHES_DIR);

        instance = new LauncherFrame();
    }

    public static LauncherFrame getInstance() {
        return instance;
    }

    public static CrashReporter getCrashReporter() {
        return crashReporter;
    }

    public LauncherPanel getLauncherPanel() {
        return this.LauncherPanel;
    }

    public static void openshaders(){
        getInstance().getContentPane().removeAll();
        getInstance().setContentPane(shaderPanel = new shaderPanel());
        SwingUtilities.updateComponentTreeUI(getInstance());
    }

    public static void open(){
        getInstance().getContentPane().removeAll();
        getInstance().setContentPane(LauncherPanel = new LauncherPanel());
        SwingUtilities.updateComponentTreeUI(getInstance());
    }
}