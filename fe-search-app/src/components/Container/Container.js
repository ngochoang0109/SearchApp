const Container=(props)=>{
    return (
        <div id="content" className="p-4 p-md-5 pt-5">
            {props.children}
       </div>
    )
}

export default Container;