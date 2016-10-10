package fiu.com.skillcourt.game;

/**
 * Created by pedrocarrillo on 10/4/16.
 */
public class SkillCourtManager {

    private SkillCourtGame game;

    private static SkillCourtManager ourInstance = new SkillCourtManager();

    public static SkillCourtManager getInstance() {
        return ourInstance;
    }

    private SkillCourtManager() {
        game = new SkillCourtGame();
    }

    public SkillCourtGame getGame() {
        return game;
    }

}
