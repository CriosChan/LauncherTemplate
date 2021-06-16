package fr.crioschan.template.launcher;

import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;


import static fr.theshark34.swinger.Swinger.getTransparentWhite;

public class shaderPanel extends JPanel implements SwingerEventListener, ActionListener, ItemListener {

    private final Image background = Swinger.getResource("bg_shaders.png");

    public static final File N_DIR = GameDirGenerator.createGameDir("SERVER NAME");

    public static Saver saver = new Saver(new File(N_DIR, "Prop.properties"));

    private final STexturedButton downloadExtremeChocapic = new STexturedButton(Swinger.getResource("transparent.png"));
    private final STexturedButton downloadUltraChocapic = new STexturedButton(Swinger.getResource("transparent.png"));
    private final STexturedButton downloadHightChocapic = new STexturedButton(Swinger.getResource("transparent.png"));
    private final STexturedButton downloadMediumChocapic = new STexturedButton(Swinger.getResource("transparent.png"));
    private final STexturedButton downloadLowChocapic = new STexturedButton(Swinger.getResource("transparent.png"));
    private final STexturedButton downloadLiteChocapic = new STexturedButton(Swinger.getResource("transparent.png"));

    private final STexturedButton downloadSonic = new STexturedButton(Swinger.getResource("transparent.png"));
    private final STexturedButton downloadKuda = new STexturedButton(Swinger.getResource("transparent.png"));
    private final STexturedButton downloadPerformance = new STexturedButton(Swinger.getResource("transparent.png"));


    private final STexturedButton quitButton = new STexturedButton(Swinger.getResource("quit.png"));
    private final STexturedButton hideButton = new STexturedButton(Swinger.getResource("reduction.png"));

    private final JLabel downloaded = new JLabel("", SwingConstants.CENTER);

    public String path = N_DIR + "/shaderpacks";

    public shaderPanel() {
        this.setLayout(null);
        setOpaque(false);

        InputStream is = null;
        Font customFont = null;
        GraphicsEnvironment ge = null;
        is = shaderPanel.class.getResourceAsStream("ressource/minecraftfont.ttf");
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(10f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);

        /**usernameField.setFont(customFont);
        usernameField.setForeground(Color.black);
        usernameField.setCaretColor(Color.black);
        usernameField.setOpaque(false);
        usernameField.setBorder(null);
        usernameField.setBounds(164, 534, 169, 26);
        this.add(usernameField);**/

        downloadExtremeChocapic.setBounds(524, 59, 166, 34);
        downloadExtremeChocapic.addEventListener(this);
        this.add(downloadExtremeChocapic);
        downloadUltraChocapic.setBounds(695, 59, 166, 34);
        downloadUltraChocapic.addEventListener(this);
        this.add(downloadUltraChocapic);
        downloadHightChocapic.setBounds(864, 59, 166, 34);
        downloadHightChocapic.addEventListener(this);
        this.add(downloadHightChocapic);
        downloadMediumChocapic.setBounds(523, 106, 166, 34);
        downloadMediumChocapic.addEventListener(this);
        this.add(downloadMediumChocapic);
        downloadLowChocapic.setBounds(694, 106, 166, 34);
        downloadLowChocapic.addEventListener(this);
        this.add(downloadLowChocapic);
        downloadLiteChocapic.setBounds(863, 106, 166, 34);
        downloadLiteChocapic.addEventListener(this);
        this.add(downloadLiteChocapic);

        downloadSonic.setBounds(869, 226, 241, 49);
        downloadSonic.addEventListener(this);
        this.add(downloadSonic);
        downloadKuda.setBounds(869, 376, 241, 49);
        downloadKuda.addEventListener(this);
        this.add(downloadKuda);
        downloadPerformance.setBounds(871, 531, 241, 53);
        downloadPerformance.addEventListener(this);
        this.add(downloadPerformance);

        quitButton.setBounds(1159, 7, 32, 32);
        quitButton.addEventListener(this);
        this.add(quitButton);

        hideButton.setBounds(1120, 25, 32, 12);
        hideButton.addEventListener(this);
        this.add(hideButton);

        //signButton.setBounds(110, 50, 115, 16);
        //this.add(signButton);

        downloaded.setForeground(Color.WHITE);
        downloaded.setFont(customFont.deriveFont(15f));
        downloaded.setOpaque(false);
        downloaded.setBorder(null);
        downloaded.setBounds(100, 610, 1000, 40);
        this.add(downloaded);

        /*signButton.addMouseListener(new MouseListener() {
            public void mouseEntered(MouseEvent e) {
                signButton.setForeground(Color.RED);
            }
            public void mouseExited(MouseEvent e) {
                signButton.setForeground(Color.white);
            }
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(URI.create("http://82.64.141.173/MineWebCMS-1.10.3/"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mousePressed(MouseEvent e) {
            }
        });
         */
    }


    @Override
    public void onEvent(SwingerEvent e) {
        if(!Files.exists(Paths.get(path))) {
            File f = new File(path);
            f.mkdir();
            System.out.print("Created");
        }
        if (e.getSource() == downloadExtremeChocapic) {
            downloaded.setText("");
            download("http://download2266.mediafire.com/8sfsgro346ag/9xo827f5sejqm5c/Chocapic13+V7.1+Extreme.zip", "ChocapicExtreme");
            downloaded.setText("Fini, vous pouvez cliquer sur la croix pour revenir au launcher");
        } else if (e.getSource() == downloadUltraChocapic) {
            downloaded.setText("");
            download("http://download2267.mediafire.com/erviwjpd2ayg/wnchvkwbw7lu1du/Chocapic13+V7.1+Ultra.zip", "ChocapicUltra");
            downloaded.setText("Fini, vous pouvez cliquer sur la croix pour revenir au launcher");
        } else if (e.getSource() == downloadHightChocapic) {
            downloaded.setText("");
            download("http://download2263.mediafire.com/eqvsb6n4xhwg/2ya34fogk8o7xl1/Chocapic13+V7.1+High.zip", "ChocapicHight");
            downloaded.setText("Fini, vous pouvez cliquer sur la croix pour revenir au launcher");
        } else if (e.getSource() == downloadMediumChocapic) {
            downloaded.setText("");
            download("http://www.mediafire.com/file/d2400ghu017571k/Chocapic13_V7.1_Medium.zip/file", "ChocapicMedium");
            downloaded.setText("Fini, vous pouvez cliquer sur la croix pour revenir au launcher");
        } else if (e.getSource() == downloadLowChocapic) {
            downloaded.setText("");
            download("http://www.mediafire.com/file/tki9avyn84bn7z5/Chocapic13_V7.1_Low.zip/file", "ChocapicLow");
            downloaded.setText("Fini, vous pouvez cliquer sur la croix pour revenir au launcher");
        } else if (e.getSource() == downloadLiteChocapic) {
            downloaded.setText("");
            download("http://www.mediafire.com/file/7fd9q6hyj4kwj19/Chocapic13_V7.1.1_Lite.zip/file", "ChocapicLite");
            downloaded.setText("Fini, vous pouvez cliquer sur la croix pour revenir au launcher");
        } else if (e.getSource() == downloadSonic) {
            downloaded.setText("");
            download("https://download2260.mediafire.com/3gy9dlcmha7g/zp3evmj53tladwd/SEUS-Renewed-v1.0.1.zip", "SEUS");
            downloaded.setText("Fini, vous pouvez cliquer sur la croix pour revenir au launcher");
        } else if (e.getSource() == downloadKuda) {
            downloaded.setText("");
            download("https://download2271.mediafire.com/cxyqwg30z7pg/6v2olkmw9r5rdyr/KUDA-Shaders-All-Versions-Medium.zip", "KudaMedium");
            downloaded.setText("Fini, vous pouvez cliquer sur la croix pour revenir au launcher");
        } else if (e.getSource() == downloadPerformance) {
            downloaded.setText("");
            download("http://download1477.mediafire.com/l610nqv26ugg/9o62itb74z9vexz/MrMeepz%5C%27+Shaders+v05+Lite.zip", "MrMeepzLite");
            downloaded.setText("Fini, vous pouvez cliquer sur la croix pour revenir au launcher");
        } else if (e.getSource() == quitButton) {
            LauncherFrame.open();
        } else if (e.getSource() == hideButton) {
            LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public void download(String url, String name){
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + name + ".zip")) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }

        } catch (IOException ev) {
            // handle exception
        }
    }

    private void setFieldsEnabled(boolean enabled) {
        downloadExtremeChocapic.setEnabled(enabled);
        downloadUltraChocapic.setEnabled(enabled);
        downloadHightChocapic.setEnabled(enabled);
        downloadMediumChocapic.setEnabled(enabled);
        downloadLowChocapic.setEnabled(enabled);
        downloadLiteChocapic.setEnabled(enabled);
        quitButton.setEnabled(enabled);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // Mots de passe
    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();

    }
}