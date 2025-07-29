package isthereanydeal.app.games.application.common;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GameAssetsUtils {
    public static String GetBanner(JsonNode assetsNode) {
        String banner = null;
        if (assetsNode != null && assetsNode.isObject()) {
            Iterator<String> fieldNames = assetsNode.fieldNames();
            List<String> keys = new ArrayList<>();
            fieldNames.forEachRemaining(keys::add);
            Collections.reverse(keys);

            for (String key : keys) {
                if (key.startsWith("banner")) {
                    banner = assetsNode.get(key).asText();
                    break;
                }
            }
        }
        return banner;
    }
}
