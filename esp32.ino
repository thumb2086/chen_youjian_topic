// Motor A (Left) Pins
#define RPWM_A 39
#define LPWM_A 40

// Motor B (Right) Pins
#define RPWM_B 41
#define LPWM_B 42

void setup() {
  // Set all motor control pins as outputs
  pinMode(RPWM_A, OUTPUT);
  pinMode(LPWM_A, OUTPUT);
  pinMode(RPWM_B, OUTPUT);
  pinMode(LPWM_B, OUTPUT);

  // Initialize Serial communication for debugging
  Serial.begin(115200);
  Serial.println("Motor control sketch started");

  // Stop motors initially
  stopMotors();
}

void loop() {
  // Example sequence:

  // Move forward at full speed for 2 seconds
  Serial.println("Moving forward");
  moveForward(255);
  delay(2000);

  // Stop for 1 second
  Serial.println("Stopping");
  stopMotors();
  delay(1000);

  // Move backward at half speed for 2 seconds
  Serial.println("Moving backward");
  moveBackward(128);
  delay(2000);

  // Stop for 1 second
  Serial.println("Stopping");
  stopMotors();
  delay(1000);
}

// Function to move both motors forward
void moveForward(int speed) {
  // Motor A forward
  analogWrite(LPWM_A, 0);
  analogWrite(RPWM_A, speed);
  
  // Motor B forward
  analogWrite(LPWM_B, 0);
  analogWrite(RPWM_B, speed);
}

// Function to move both motors backward
void moveBackward(int speed) {
  // Motor A backward
  analogWrite(RPWM_A, 0);
  analogWrite(LPWM_A, speed);

  // Motor B backward
  analogWrite(RPWM_B, 0);
  analogWrite(LPWM_B, speed);
}

// Function to stop both motors
void stopMotors() {
  // Stop Motor A
  analogWrite(RPWM_A, 0);
  analogWrite(LPWM_A, 0);
  
  // Stop Motor B
  analogWrite(RPWM_B, 0);
  analogWrite(LPWM_B, 0);
}
