import Button from "../Button";
import diagram from "../../assets/test-diagram.png";

export default function DashboardWeeklyScore() {
    return (
        <section className="w-full px-12 flex flex-col gap-10 justify-center items-center">
            <div className="w-full relative flex items-center justify-center">
                <h3 className="text-2xl uppercase">Weekly Score</h3>
                <Button text="View More" style="absolute right-0" />
            </div>
            <div className="w-full px-12">
                <img className="w-3/4 mx-auto" src={diagram} />
            </div>
        </section>
    )
}