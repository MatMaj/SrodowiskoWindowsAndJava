package interfaces;

import model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmloyeeDAO {
    /** * Zwraca pracownika o podanym id */
    Optional findOne(Integer id);
    /** * Zwraca wszystkich pracowników */
    List findAll();
    /** * Zwraca pracownika o podanym nazwisku */
    Optional findByName(String name);
    /** * Usuwa pracownika */
    void delete(Employee employee);
    /** * Dodaje, jeśli brak, lub aktualizuje pracownika */
    void save(Employee employee);
    /** * Modyfikacja danych */
    void modify(Long id, String name, String email, Double salary);
}
