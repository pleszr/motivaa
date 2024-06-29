import diagram from "../../assets/test-diagram.png";

export default function Overview() {
    return (
        <section className="w-full pt-12 flex flex-col gap-12">
            <div className="w-full px-12">
                <img className="w-3/4 mx-auto" src={diagram} />
            </div>
        </section>
    )
}