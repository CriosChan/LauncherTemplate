package fr.crioschan.template.launcher;

import fr.litarvan.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Exception;

import static fr.theshark34.swinger.Swinger.getTransparentWhite;

public class LauncherPanel extends JPanel implements SwingerEventListener, ActionListener, ItemListener {

    private final Image background = Swinger.getResource("bg.png");

    public static final File N_DIR = GameDirGenerator.createGameDir("SERVER NAME");

    public static Saver saver = new Saver(new File(N_DIR, "Prop.properties"));
    public static RamSelector ramSelector = new RamSelector(new File(N_DIR, "ram.txt"));


    public static JTextField usernameField = new JTextField(saver.get("username"));
    public static JTextField passwordField = new JPasswordField(saver.get("password"));

    private final STexturedButton playButton = new STexturedButton(Swinger.getResource("play.png"));
    private final STexturedButton quitButton = new STexturedButton(Swinger.getResource("quit.png"));
    private final STexturedButton hideButton = new STexturedButton(Swinger.getResource("reduction.png"));
    //private STexturedButton signButton = new STexturedButton(Swinger.getResource("SignUp.png"));
    public JCheckBox checkBoxPassword = new JCheckBox(saver.get("passwordCheckBox"));

    public boolean PassSave = false;

    java.awt.Font info = new java.awt.Font("Arial", Font.PLAIN, 20);

    private final SColoredBar progressBar = new SColoredBar(getTransparentWhite(100), getTransparentWhite(175));
    private final JLabel infoText = new JLabel("Click on LOGIN", SwingConstants.CENTER);
    private final JLabel passwordBox = new JLabel("Save password ?");

    private final STexturedButton ramButton = new STexturedButton(Swinger.getResource("ram.png"));
    private final STexturedButton shaderButton = new STexturedButton(Swinger.getResource("transparent.png"));


    public LauncherPanel() {
        this.setLayout(null);
        setOpaque(false);

        InputStream is = null;
        Font customFont = null;
        GraphicsEnvironment ge = null;
        is = LauncherPanel.class.getResourceAsStream("ressource/minecraftfont.ttf");
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(10f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);

        usernameField.setFont(customFont);
        usernameField.setForeground(Color.black);
        usernameField.setCaretColor(Color.black);
        usernameField.setOpaque(false);
        usernameField.setBorder(null);
        usernameField.setBounds(164, 534, 169, 26);
        this.add(usernameField);

        passwordField.setForeground(Color.black);
        passwordField.setCaretColor(Color.black);
        passwordField.setOpaque(false);
        passwordField.setBorder(null);
        passwordField.setBounds(185, 585, 148, 27);
        this.add(passwordField);

        playButton.setBounds(908, 557, 165, 32);
        playButton.addEventListener(this);
        this.add(playButton);

        quitButton.setBounds(1159, 7, 32, 32);
        quitButton.addEventListener(this);
        this.add(quitButton);

        hideButton.setBounds(1120, 25, 32, 12);
        hideButton.addEventListener(this);
        this.add(hideButton);

        progressBar.setBounds(10, 650, 1180, 18);
        this.add(progressBar);

        //signButton.setBounds(110, 50, 115, 16);
        //this.add(signButton);

        infoText.setForeground(Color.WHITE);
        infoText.setFont(usernameField.getFont().deriveFont(20f));
        infoText.setBounds(330, 610, 500, 40);
        this.add(infoText);

        checkBoxPassword.setBounds(55, 625, 20, 20);
        checkBoxPassword.addItemListener(this);
        this.add(checkBoxPassword);

        passwordBox.setFont(usernameField.getFont());
        passwordBox.setForeground(Color.WHITE);
        passwordBox.setBounds(80, 615, 500, 40);
        this.add(passwordBox);

        this.ramButton.addEventListener(this);
        this.ramButton.setBounds(1080,7,32,32);
        this.add(ramButton);

        this.shaderButton.addEventListener(this);
        this.shaderButton.setBounds(3,2,152,33);
        this.add(shaderButton);

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
        if (e.getSource() == playButton) {
            setFieldsEnabled(false);

            Thread t = new Thread() {
                @Override
                public void run() {
                    if(passwordField.getText().length() > 0) {
                        try {
                            if (saver.get("passwordCheckBox") == "true") {
                                PassSave = true;
                            }
                            Launcher.auth(usernameField.getText(), passwordField.getText());
                            saver.set("username", usernameField.getText());
                            if (PassSave) {
                                saver.set("password", passwordField.getText());
                            }
                            Crypto.Encrypt();
                        } catch (AuthenticationException e) {
                            JOptionPane.showMessageDialog(LauncherPanel.this, "Error, unable to connect: " + e.getErrorModel().getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            setFieldsEnabled(true);
                            return;
                        }
                    }
                    try {
                        Launcher.update();
                    } catch (Exception e) {
                        Launcher.interruptThread();
                        LauncherFrame.getCrashReporter().catchError(e, "Error, unable to launch SERVER NAME");
                        System.exit(1);
                    }



                    try {
                        Launcher.launch();
                    } catch (LaunchException e1) {
                        e1.printStackTrace();
                    }
                }
            };
            t.start();
        } else if (e.getSource() == quitButton) {
            Animator.fadeOutFrame(LauncherFrame.getInstance(), 5, new Runnable() {
                @Override
                public void run() {
                    Crypto.Encrypt();
                    System.exit(0);
                }
            });
        } else if (e.getSource() == hideButton) {
            LauncherFrame.getInstance().setState(JFrame.ICONIFIED);
        } else if (e.getSource() == this.ramButton) {
            ramSelector.setFrameClass(TemplateRamSelectorWindow.class);
            ramSelector.display();
        } else if (e.getSource() == this.shaderButton) {LauncherFrame.openshaders();}
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    private void setFieldsEnabled(boolean enabled) {
        usernameField.setEnabled(enabled);
        passwordField.setEnabled(enabled);
        playButton.setEnabled(enabled);
        quitButton.setEnabled(enabled);
        passwordBox.setEnabled(enabled);
        checkBoxPassword.setEnabled(enabled);
        ramButton.setEnabled(enabled);
        //signButton.setEnabled(enabled);
    }

    public SColoredBar getProgressBar() {
        return progressBar;
    }

    public void setInfoText(String text) {
        infoText.setText(text);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // Mots de passe
    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();

        if (source == checkBoxPassword) {
            PassSave = true;
            saver.set("passwordCheckBox", "true");
        }
    }

    public RamSelector getRamSelector() {
        return ramSelector;
    }
}