package examples.scheduler.microservices.delivery.schedule.config;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class MongoConfig {

	/*
	 * 
	 * NOTE: This class may not cover the actual needs of a real application. It is
	 * using the current system's ZoneId to convert the Date back to an
	 * OffsetDateTime. This will only work if everything uses that same ZoneId
	 * default. Otherwise, need to find another way to store the correct
	 * ZoneId/Offset along with the Date for proper conversion.
	 * 
	 */

	@Bean
	public MongoCustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(OffsetDateTimeToDateConverter.INSTANCE);
		converters.add(DateToOffsetDateTimeConverter.INSTANCE);
		converters.add(HardMediumSoftScoreToString.INSTANCE);
		converters.add(StringToHardMediumSoftScore.INSTANCE);
		return new MongoCustomConversions(converters);
	}

	enum OffsetDateTimeToDateConverter implements Converter<OffsetDateTime, Date> {

		INSTANCE;

		@Override
		public Date convert(OffsetDateTime source) {
			return Date.from(source.toInstant());
		}

	}

	enum DateToOffsetDateTimeConverter implements Converter<Date, OffsetDateTime> {

		INSTANCE;

		@Override
		public OffsetDateTime convert(Date source) {
			return OffsetDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
		}

	}

	enum HardMediumSoftScoreToString implements Converter<HardMediumSoftScore, String> {

		INSTANCE;

		@Override
		public String convert(HardMediumSoftScore source) {
			return source.toString();
		}

	}

	enum StringToHardMediumSoftScore implements Converter<String, HardMediumSoftScore> {

		INSTANCE;

		@Override
		public HardMediumSoftScore convert(String source) {
			return HardMediumSoftScore.parseScore(source);
		}

	}

}
