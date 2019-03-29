package examples.scheduler.microservices.delivery.schedule.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import examples.scheduler.domain.common.AbstractPersistable;
import examples.scheduler.domain.exception.RepositoryException;
import examples.scheduler.domain.exception.ResourceAlreadyExistsException;
import examples.scheduler.domain.exception.ResourceNotFoundException;

public abstract class AbstractMongoService<T extends AbstractPersistable> {

	protected MongoRepository<T, String> repository;

	public AbstractMongoService(MongoRepository<T, String> repository) {
		this.repository = repository;
	}

	public T create(T persistable) {

		T obj = null;

		// see if object exists with id
		Optional<T> optional = repository.findById(persistable.getId());

		// throw exception if already exists
		if (optional.isPresent()) {
			throw new ResourceAlreadyExistsException("resource already exists with id '" + persistable.getId() + "'");
		}

		// save the new object to the data store
		try {
			obj = repository.save(persistable);
		} catch (RuntimeException e) {
			throw new RepositoryException("failed to create resource for " + persistable, e);
		}

		// return saved object
		return obj;

	}

	public T update(T persistable) {

		// validate object already exists
		T object = get(persistable.getId());

		// set id on object to update
		persistable.setId(object.getId());

		// save
		try {
			repository.save(persistable);
		} catch (RuntimeException e) {
			throw new RepositoryException("failed to update resource " + persistable, e);
		}

		// return saved object
		return persistable;

	}

	public T get(String id) {

		// find by id
		Optional<T> optional = repository.findById(id);

		// throw not found exception if it doesn't exist
		if (!optional.isPresent()) {
			throw new ResourceNotFoundException("no resource with id '" + id + "'");
		}

		// return object found
		return optional.get();

	}

	public Collection<T> getAll() {

		// get all objects
		return repository.findAll();

	}

	public T delete(String id) {

		// get object or throw not found exception
		T obj = get(id);

		// delete object by id
		repository.deleteById(obj.getId());
		;

		// return object deleted
		return obj;

	}

	public Collection<T> deleteAll() {

		// get all before deleting
		Collection<T> collection = getAll();

		// delete
		repository.deleteAll();

		// return what was deleted
		return collection;

	}

}
