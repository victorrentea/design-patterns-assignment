package victor.training.patterns.assignment;

public class UserProfileDto {
   private Long id;
   private String fullName;
   private Integer age;
   private String gisHome;
   private String phoneNumber;
   private Integer hoursRided;
   private Float averageSpeed;
   private Float averageRideSeconds;
   private Float averageRidePower;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public Integer getAge() {
      return age;
   }

   public void setAge(Integer age) {
      this.age = age;
   }

   public String getGisHome() {
      return gisHome;
   }

   public void setGisHome(String gisHome) {
      this.gisHome = gisHome;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public Integer getHoursRided() {
      return hoursRided;
   }

   public void setHoursRided(Integer hoursRided) {
      this.hoursRided = hoursRided;
   }

   public Float getAverageSpeed() {
      return averageSpeed;
   }

   public void setAverageSpeed(Float averageSpeed) {
      this.averageSpeed = averageSpeed;
   }

   public Float getAverageRideSeconds() {
      return averageRideSeconds;
   }

   public void setAverageRideSeconds(Float averageRideSeconds) {
      this.averageRideSeconds = averageRideSeconds;
   }

   public Float getAverageRidePower() {
      return averageRidePower;
   }

   public void setAverageRidePower(Float averageRidePower) {
      this.averageRidePower = averageRidePower;
   }
}
