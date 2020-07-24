package principal;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TestDuration {

	public static void main(String[] args) {
		long f1 = 40566619488995L;
		long f2 = 22342367L;
		
		LocalDateTime ld1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(f1), ZoneId.systemDefault());
		LocalDateTime ld2 = LocalDateTime.ofInstant(Instant.ofEpochMilli(f2), ZoneId.systemDefault());;
		
		Duration duration = Duration.between(ld2, ld1);
		
		System.out.println(duration);
		System.out.println("Dias: "+duration.toDays());
		
	}

}
