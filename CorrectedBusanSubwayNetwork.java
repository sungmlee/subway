import java.util.*;

public class CorrectedBusanSubwayNetwork {
    // 각 역을 나타내는 클래스
    static class Station {
        String name; // 역 이름
        Map<String, Integer> connections; // 이웃역과의 연결 정보 (역 이름, 소요 시간)

        public Station(String name) {
            this.name = name;
            this.connections = new HashMap<>();
        }

        // 이웃 역과의 연결 추가
        public void addConnection(String neighbor, int time) {
            this.connections.put(neighbor, time);
        }
    }

    // 지하철 네트워크를 나타내는 클래스
    static class SubwayNetwork {
        Map<String, Station> stations; // 역 이름과 Station 객체를 저장하는 맵

        public SubwayNetwork() {
            stations = new HashMap<>();
        }

        // 역 추가
        public void addStation(String name) {
            stations.putIfAbsent(name, new Station(name));
        }

        // 두 역 간의 연결 추가
        public void connectStations(String from, String to, int time) {
            stations.get(from).addConnection(to, time);
            stations.get(to).addConnection(from, time);
        }

        // 최단 경로를 계산하는 메소드 (다익스트라 알고리즘 사용)
        public int findShortestPath(String start, String destination) {
            Map<String, Integer> distances = new HashMap<>(); // 각 역까지의 최단 거리
            Map<String, String> previous = new HashMap<>(); // 최단 경로 추적용

            // 모든 역의 초기 거리를 무한대로 설정
            for (String station : stations.keySet()) {
                distances.put(station, Integer.MAX_VALUE);
            }
            distances.put(start, 0); // 출발 역의 거리는 0

            // 우선순위 큐를 사용해 최소 거리 역 탐색
            PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
            pq.add(start);

            while (!pq.isEmpty()) {
                String current = pq.poll(); // 현재 처리 중인 역
                Station currentStation = stations.get(current);

                // 현재 역과 연결된 모든 이웃 역 확인
                for (Map.Entry<String, Integer> neighbor : currentStation.connections.entrySet()) {
                    String neighborName = neighbor.getKey();
                    int time = neighbor.getValue();

                    // 새로운 거리 계산
                    int newDist = distances.get(current) + time;
                    if (newDist < distances.get(neighborName)) {
                        distances.put(neighborName, newDist); // 최단 거리 갱신
                        previous.put(neighborName, current); // 경로 추적
                        pq.add(neighborName); // 큐에 추가
                    }
                }
            }

            // 최단 경로를 리스트로 추적
            List<String> path = new ArrayList<>();
            String current = destination;
            while (current != null) {
                path.add(0, current);
                current = previous.get(current);
            }

            // 경로 출력
            System.out.println("Path: " + String.join(" -> ", path));
            return distances.get(destination); // 최단 거리 반환
        }
    }

    public static void main(String[] args) {
        SubwayNetwork network = new SubwayNetwork();

        // 부산 1호선
        String[] line1 = {
            "다대포해수욕장", "다대포항", "낫개", "신장림", "장림", "동매", "신평", "하단", "당리",
            "사하", "괴정", "대티", "서대신", "동대신", "토성", "자갈치", "남포", "중앙",
            "부산역", "초량", "부산진", "좌천", "범일", "범내골", "서면", "부전", "양정", "시청",
            "연산", "교대", "동래", "명륜", "온천장", "부산대", "장전", "구서", "두실", "남산", "범어사", "노포"
        };
        int[] line1Times = {3, 2, 3, 3, 2, 3, 3, 2, 2, 2, 3, 3, 2, 2, 2, 2, 2, 3, 2, 2, 2, 3, 3, 2, 2, 2, 2, 3, 2, 2, 3, 2, 2, 3, 3, 2, 2};
        for (int i = 0; i < Math.min(line1.length - 1, line1Times.length); i++) {
            network.addStation(line1[i]);
            network.addStation(line1[i + 1]);
            network.connectStations(line1[i], line1[i + 1], line1Times[i]);
        }

        // 부산 2호선
        String[] line2 = {
            "장산", "중동", "해운대", "동백", "벡스코", "센텀시티", "민락", "수영", "광안",
            "금련산", "남천", "경성대부경대", "대연", "못골", "지게골", "문현", "국제금융센터부산은행",
            "전포", "서면", "부암", "가야", "동의대", "개금", "냉정", "주례", "감전", "사상",
            "덕포", "모덕", "모라", "구남", "구명", "덕천", "수정", "화명", "율리", "동원", "금곡", "호포", "증산", "부산대양산캠퍼스", "남양산", "양산"
        };
        int[] line2Times = {2, 2, 3, 2, 3, 2, 2, 2, 3, 2, 2, 3, 2, 2, 2, 3, 2, 2, 2, 2, 3, 2, 3, 2, 2, 2, 3, 2, 3, 2, 2, 3, 2, 3, 2, 3, 3, 3, 2, 2, 3};

        for (int i = 0; i < Math.min(line2.length - 1, line2Times.length); i++) {
            network.addStation(line2[i]);
            network.addStation(line2[i + 1]);
            network.connectStations(line2[i], line2[i + 1], line2Times[i]);
        }
        

        // 부산 3호선
        String[] line3 = {
            "수영", "망미", "배산", "물만골", "연산", "종합운동장", "사직", "미남", "만덕", "남산정",
            "숙등", "덕천", "구포", "강서구청", "체육공원", "대저"
        };
        int[] line3Times = {2, 3, 2, 2, 3, 3, 2, 3, 3, 2, 3, 3, 2, 3, 2};
        for (int i = 0; i < Math.min(line3.length - 1, line3Times.length); i++) {
            network.addStation(line3[i]);
            network.addStation(line3[i + 1]);
            network.connectStations(line3[i], line3[i + 1], line3Times[i]);
        }

        // 부산 4호선
        String[] line4 = {
            "미남", "동래", "수안", "낙민", "충렬사", "명장", "서동", "금사", "반여농산물시장", "석대",
            "영산대", "반여", "재송", "센텀"
        };
        int[] line4Times = {3, 2, 2, 3, 2, 2, 3, 2, 2, 3, 3, 2, 2};
        for (int i = 0; i < Math.min(line4.length - 1, line4Times.length); i++) {
            network.addStation(line4[i]);
            network.addStation(line4[i + 1]);
            network.connectStations(line4[i], line4[i + 1], line4Times[i]);
        }

        // 환승 정보
        network.connectStations("서면", "서면", 5); // 1호선 - 2호선
        network.connectStations("덕천", "덕천", 5); // 2호선 - 3호선
        network.connectStations("미남", "미남", 5); // 3호선 - 4호선
        network.connectStations("동래", "동래", 5); // 1호선 - 4호선

        // 사용자 입력
        Scanner scanner = new Scanner(System.in);
        System.out.print("출발역을 입력하세요: ");
        String start = scanner.nextLine();
        System.out.print("도착역을 입력하세요: ");
        String destination = scanner.nextLine();

        if (!network.stations.containsKey(start) || !network.stations.containsKey(destination)) {
            System.out.println("잘못된 역 이름입니다. 프로그램을 종료합니다.");
        } else {
            int shortestTime = network.findShortestPath(start, destination);
            System.out.println("최단 시간: " + shortestTime + " 분");
        }
    }
}