select c.CI_ID, wm_concat(s.stu_name) namelist
from pm_ci c, pm_stu s
where instr(c.stu_ids,s.stu_id) > 0
group by c.CI_ID;