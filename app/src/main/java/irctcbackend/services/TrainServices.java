package irctcbackend.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import irctcbackend.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TrainServices {

    private Train train;

    private List<Train> trainList;

    private static final String TRAIN_PATH = "app/src/main/java/irctcbackend/localDB/train.json";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void loadTrains() throws IOException {
        File files = new File(TRAIN_PATH);
        objectMapper.readValue(files, new TypeReference<List<Train>>() {
        });
    }

    public TrainServices() throws IOException {
        loadTrains();
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
}
