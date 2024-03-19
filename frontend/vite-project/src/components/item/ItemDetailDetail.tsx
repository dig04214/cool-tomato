export default function ItemDetailDetail({content}:{content : string | undefined}) {
    const MoveTop = () => {
        window.scrollTo({ top: 0 })
    }
    MoveTop()

    return (
        <>
            <div dangerouslySetInnerHTML = {{__html : (content ? content : "")}}></div>
        </>
    );
}
