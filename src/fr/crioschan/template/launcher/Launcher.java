package fr.crioschan.template.launcher;

import fr.litarvan.openauth.AuthPoints;
import fr.litarvan.openauth.AuthenticationException;
import fr.litarvan.openauth.Authenticator;
import fr.litarvan.openauth.model.AuthAgent;
import fr.litarvan.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.Swinger;

import java.io.File;
import java.util.Arrays;

public class Launcher extends LauncherPanel {
    public static final GameVersion MC_VERSION = new GameVersion("1.12.2", GameType.V1_8_HIGHER);
    public static final File N_DIR = GameDirGenerator.createGameDir("SERVER NAME");
    public static final GameInfos N_INFOS = new GameInfos("SERVER NAME", N_DIR, MC_VERSION, new GameTweak[]{GameTweak.FORGE});
    public static final File N_CRASHES_DIR = new File(N_DIR, "crashes");

    private static AuthInfos authInfos;

    public static void auth(String username, String password) throws AuthenticationException {
        Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
        AuthResponse response = authenticator.authenticate(AuthAgent.MINECRAFT, username, password, "");
        authInfos = new AuthInfos(response.getSelectedProfile().getName(), response.getAccessToken(), response.getSelectedProfile().getId());

    }

    private static Thread updateThread;

    public static void update() throws Exception {
        SUpdate su = new SUpdate("SUPDATE LINK SERVER", N_DIR);
        su.addApplication(new FileDeleter());

        updateThread = new Thread() {

            int val = 0;
            int max = 0;

            @Override
            public void run() {
                while (!this.isInterrupted()) {
                    if (BarAPI.getNumberOfFileToDownload() == 0) {
                        LauncherFrame.getInstance().getLauncherPanel().setInfoText("File verification...");
                        continue;
                    }

                    val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000);
                    max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000);
                    LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(max);
                    LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setValue(val);
                    LauncherFrame.getInstance().getLauncherPanel().setInfoText("Downloading the files : " +
                            BarAPI.getNumberOfDownloadedFiles() + " / " + BarAPI.getNumberOfFileToDownload() + " " +
                            Swinger.percentage(val, max) + "%");
                }
            }
        };
        updateThread.start();
        su.start();
        updateThread.interrupt();
    }

    public static void launch() throws LaunchException {
        ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(N_INFOS, GameFolder.BASIC, authInfos);
        profile.getVmArgs().addAll(Arrays.asList(LauncherFrame.getInstance().getLauncherPanel().getRamSelector().getRamArguments()));
        ExternalLauncher launcher = new ExternalLauncher(profile);
        Process p = launcher.launch();

        try {
            Thread.sleep(5000L);
            LauncherFrame.getInstance().setVisible(false);
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void interruptThread() {
        updateThread.interrupt();
    }

}