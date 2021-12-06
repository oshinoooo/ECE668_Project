import controller.CacheSimulatorSystem;
import view.CacheSimulatorView;

public class CacheSimulator {
    public static void main(String[] args) {
        CacheSimulatorSystem cacheSimulatorSystem = new CacheSimulatorSystem();
        CacheSimulatorView cacheSimulatorView = new CacheSimulatorView(cacheSimulatorSystem);
        cacheSimulatorSystem.addPropertyChangeListener(cacheSimulatorView);
    }
}
