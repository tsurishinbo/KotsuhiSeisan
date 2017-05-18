/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tsurishinbo
 */
@Entity
@Table(name = "m_employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MEmployee.findAll", query = "SELECT m FROM MEmployee m")
    , @NamedQuery(name = "MEmployee.findById", query = "SELECT m FROM MEmployee m WHERE m.id = :id")
    , @NamedQuery(name = "MEmployee.findByPassword", query = "SELECT m FROM MEmployee m WHERE m.password = :password")
    , @NamedQuery(name = "MEmployee.findByEmployeeName", query = "SELECT m FROM MEmployee m WHERE m.employeeName = :employeeName")
    , @NamedQuery(name = "MEmployee.findByBossId", query = "SELECT m FROM MEmployee m WHERE m.bossId = :bossId")
    , @NamedQuery(name = "MEmployee.findByManager", query = "SELECT m FROM MEmployee m WHERE m.manager = :manager")
    , @NamedQuery(name = "MEmployee.findByIdAndPassword", query = "SELECT m FROM MEmployee m WHERE m.id = :id and m.password = :password")})
public class MEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "employee_name")
    private String employeeName;
    @Column(name = "boss_id")
    private Integer bossId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "manager")
    private int manager;

    public MEmployee() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getBossId() {
        return bossId;
    }

    public void setBossId(Integer bossId) {
        this.bossId = bossId;
    }

    public int getManager() {
        return manager;
    }

    public void setManager(int manager) {
        this.manager = manager;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MEmployee)) {
            return false;
        }
        MEmployee other = (MEmployee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MEmployee[ id=" + id + " ]";
    }
    
}
