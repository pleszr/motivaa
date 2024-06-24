export default function TextInput({text}) {
  return (
    <div className="group flex flex-col gap-4">
      <label className="uppercase pl-6" htmlFor="title">
        {text}
      </label>
      <input
        className="h-16 min-w-[300px] p-2 px-4 bg-white uppercase border-2 border-purple rounded-2xl outline-none group-hover:border-4 transition-all duration-200"
        type="text"
        id={text}
        placeholder={text}
      />
    </div>
  );
}