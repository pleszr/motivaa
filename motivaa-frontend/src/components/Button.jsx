export default function Button({text, style}) {
    // style prop is to be able to add custom styles to different buttons while still having the basic styles
    return (
        <button className={`px-4 py-2 text-white rounded-xl bg-purple hover:bg-purple/80 transition-all duration-200 ${style}`}>{text}</button>
    )
}