package com.fasoo.springredisdemo.repository;

import com.fasoo.springredisdemo.domain.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRedisRepository extends CrudRepository<Point, String> {
}
