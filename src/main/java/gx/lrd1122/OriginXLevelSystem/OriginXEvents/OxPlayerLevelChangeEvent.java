package gx.lrd1122.OriginXLevelSystem.OriginXEvents;

import gx.lrd1122.OriginXLevelSystem.OriginXLevelCore.OxLevelPlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OxPlayerLevelChangeEvent extends Event implements Cancellable {
    private static HandlerList handlerList = new HandlerList();
    private OxLevelPlayer levelPlayer;
    private int previousLevel;
    private int currentLevel;
    private double changedExp;
    private Class plugin;
    private boolean isCancelled;

    /**
     * 还没有正式加入到插件内,请勿使用
     * @param levelPlayer 插件中的玩家类,可获取到玩家本身
     * @param previousLevel 改变前玩家的等级
     * @param currentLevel 改变后玩家的等级
     * @param changedExp 改变的经验值
     * @param plugin 所注册的插件类
     */
    public OxPlayerLevelChangeEvent(OxLevelPlayer levelPlayer, int previousLevel,
                                    int currentLevel, double changedExp, Class plugin){
        this.levelPlayer = levelPlayer;
        this.previousLevel = previousLevel;
        this.currentLevel = currentLevel;
        this.changedExp = changedExp;
        this.plugin = plugin;
    }
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static void setHandlerList(HandlerList handlerList) {
        OxPlayerLevelChangeEvent.handlerList = handlerList;
    }

    public OxLevelPlayer getLevelPlayer() {
        return levelPlayer;
    }

    public void setLevelPlayer(OxLevelPlayer levelPlayer) {
        this.levelPlayer = levelPlayer;
    }

    public int getPreviousLevel() {
        return previousLevel;
    }

    public void setPreviousLevel(int previousLevel) {
        this.previousLevel = previousLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public double getChangedExp() {
        return changedExp;
    }

    public void setChangedExp(double changedExp) {
        this.changedExp = changedExp;
    }

    public Class getPlugin() {
        return plugin;
    }

    public void setPlugin(Class plugin) {
        this.plugin = plugin;
    }
}
