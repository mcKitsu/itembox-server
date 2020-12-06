package net.mckitsu.itembox.server;

import java.util.Map;
import java.util.logging.Logger;

public class ItemBox {
    public Logger logger;
    public Runnable stopService;
    public Map<String, String> tokens;
    /* **************************************************************************************
     *  Abstract method
     */

    /* **************************************************************************************
     *  Construct method
     */

    /* **************************************************************************************
     *  Override method
     */

    /* **************************************************************************************
     *  Public method
     */

    /* **************************************************************************************
     *  protected method
     */

    /* **************************************************************************************
     *  Private method
     */

    /* **************************************************************************************
     *  Static method/variable
     */
    private static ItemBox itemBox;

    protected static void setItemBox(ItemBox itemBox){
        ItemBox.itemBox = itemBox;
    }

    public static Logger getLogger(){
        return ItemBox.itemBox.logger;
    }

    public static void stopService(){
        ItemBox.itemBox.stopService.run();
    }

    public static String getTokenType(String token){
        if(token == null)
            return "unknown";

        String type = ItemBox.itemBox.tokens.get(token);

        if(type == null)
            return "unknown";
        else
            return type;
    }
}
