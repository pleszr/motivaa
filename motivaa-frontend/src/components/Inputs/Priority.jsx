export default function Priority() {
    // logic for different prio values to attach to habits (I guess it would only matter in the calculation of scores)
    return (
        <div className="w-full relative flex flex-col gap-4">
            <label className="uppercase pl-6" htmlFor="slider">Priority</label>
            <input  type="range" min="0" max="100" step="25"/>
            <div className="w-full px-4 absolute bottom-0 flex justify-between items-center">
                <span className="uppercase cursor-default">Whatever</span>
                <span className="uppercase cursor-default text-white">Must have</span>
            </div>

        </div>
    )
}