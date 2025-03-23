package irctcbackend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import irctcbackend.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainServices {

    private Train train;

    private List<Train> trainList;

    private static final String TRAIN_PATH = "app/src/main/java/irctcbackend/localDB/train.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Train> loadTrains() throws IOException {
        File files = new File(TRAIN_PATH);
        if(!files.exists()){
            return new ArrayList<>();
        }
        return objectMapper.readValue(files, new TypeReference<List<Train>>() {
        });
    }

    public TrainServices() throws IOException {
        this.trainList = loadTrains();
    }

    public List<Train> searchTrains(String source, String destination){
       return trainList.stream().filter(train1 -> validTrain(train1, source, destination)).collect(Collectors.toList());
    }
     private boolean validTrain(Train train1, String source, String destination){
        List<String> stationOrder = train1.getStations();

        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
     }

    public Train findTrain(String source, String destination) {
        for (Train train : trainList) {
            List<String> stations = train.getStations();
            if (stations.contains(source) && stations.contains(destination)) {
                return train;
            }
        }
        return null;
    }
    public int[] findAvailableSeat(Train train) {
        List<List<Integer>> seats = train.getSeats();
        for (int row = 0; row < seats.size(); row++) {
            for (int col = 0; col < seats.get(row).size(); col++) {
                if (seats.get(row).get(col) == 1) {  // 1 = available
                    return new int[]{row, col};  // Return seat position
                }
            }
        }
        return null;  // No available seat found
    }

}
