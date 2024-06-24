import DashboardHabits from "./DashboardHabits";
import DashboardWeeklyScore from "./DashboardWeeklyScore";

export default function Dashboard() {
    return (
        <section className="flex flex-col gap-20 pb-12">
            <DashboardHabits />
            <DashboardWeeklyScore />
        </section>
    )
}