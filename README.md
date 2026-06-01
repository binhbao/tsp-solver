# Traveling Salesman Problem (TSP) Solver

## Giới thiệu

Đồ án môn học triển khai và so sánh nhiều thuật toán giải bài toán Người Du Lịch (Traveling Salesman Problem - TSP).

Mục tiêu là tìm chu trình ngắn nhất đi qua tất cả các thành phố đúng một lần và quay trở lại điểm xuất phát.

---

## Cấu trúc Project

```text
src/
│
├── model/
│   ├── City.java
│   ├── Tour.java
│   └── TSPProblem.java
│
├── algorithms/
│   ├── TSPSolver.java
│   ├── Greedy.java
│   ├── SA.java
│   ├── GA.java
│   ├── BranchAndBound.java
│   └── ACO.java
│
├── utils/
│   ├── DistanceUtil.java
│   └── FileLoader.java
│
└── Main.java


Mô tả các package
1. model

Chứa các lớp biểu diễn dữ liệu của bài toán.

Class	Chức năng
City	Lưu tọa độ thành phố
Tour	Biểu diễn một lời giải TSP
TSPProblem	Lưu danh sách thành phố và ma trận khoảng cách
2. algorithms

Chứa các thuật toán giải TSP
Thuật toán	                  Loại
Greedy	                      Heuristic
Simulated Annealing	          Metaheuristic
Genetic Algorithm	          Evolutionary Algorithm
Branch and Bound	          Exact Algorithm
Ant Colony Optimization	      Swarm Intelligence
3. utils

Các lớp hỗ trợ.

Class	        Chức năng
DistanceUtil	Tính khoảng cách Euclidean
FileLoader	    Đọc dữ liệu từ file
# Các thuật toán triển khai 
1. Greedy

Thuật toán Nearest Neighbor.

Tại mỗi bước chọn thành phố gần nhất chưa được thăm.

Ưu điểm:

Rất nhanh
Dễ cài đặt

Nhược điểm:

Không đảm bảo tối ưu
2. Simulated Annealing (SA)

Mô phỏng quá trình tôi luyện kim loại.

Ý tưởng:

Chấp nhận nghiệm xấu với xác suất nhất định
Tránh rơi vào cực tiểu cục bộ
3. Genetic Algorithm (GA)

Mô phỏng quá trình tiến hóa sinh học.

Các thành phần:

Population
Selection
Crossover
Mutation
4. Branch and Bound (BnB)

Thuật toán tìm nghiệm tối ưu.

Sử dụng:

Branching
Lower Bound
Pruning

để giảm số lượng trạng thái cần duyệt.

5. Ant Colony Optimization (ACO)

Mô phỏng hành vi tìm đường của đàn kiến.

Sử dụng:

Pheromone
Heuristic Distance
Evaporation

để tìm lời giải tốt.