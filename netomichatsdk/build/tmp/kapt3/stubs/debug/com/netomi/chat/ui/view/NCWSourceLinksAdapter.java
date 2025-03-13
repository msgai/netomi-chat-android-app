package com.netomi.chat.ui.view;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0014B3\u0012\u0016\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006\u0012\u0014\u0010\u0007\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016J\u001c\u0010\r\u001a\u00020\t2\n\u0010\u000e\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\fH\u0016J\u001c\u0010\u0010\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fH\u0016R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/netomi/chat/ui/view/NCWSourceLinksAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/netomi/chat/ui/view/NCWSourceLinksAdapter$ViewHolder;", "items", "Ljava/util/ArrayList;", "Lcom/netomi/chat/model/messages/MultipleSourceDetail;", "Lkotlin/collections/ArrayList;", "multipleSourceDetail", "Lkotlin/Function1;", "", "(Ljava/util/ArrayList;Lkotlin/jvm/functions/Function1;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "netomichatsdk_debug"})
public final class NCWSourceLinksAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.netomi.chat.ui.view.NCWSourceLinksAdapter.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayList<com.netomi.chat.model.messages.MultipleSourceDetail> items = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.netomi.chat.model.messages.MultipleSourceDetail, kotlin.Unit> multipleSourceDetail = null;
    
    public NCWSourceLinksAdapter(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.netomi.chat.model.messages.MultipleSourceDetail> items, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.netomi.chat.model.messages.MultipleSourceDetail, kotlin.Unit> multipleSourceDetail) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.netomi.chat.ui.view.NCWSourceLinksAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.netomi.chat.ui.view.NCWSourceLinksAdapter.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010\u00a8\u0006\u0013"}, d2 = {"Lcom/netomi/chat/ui/view/NCWSourceLinksAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/netomi/chat/ui/view/NCWSourceLinksAdapter;Landroid/view/View;)V", "constRow", "Landroidx/constraintlayout/widget/ConstraintLayout;", "getConstRow", "()Landroidx/constraintlayout/widget/ConstraintLayout;", "imgSource", "Landroid/widget/ImageView;", "getImgSource", "()Landroid/widget/ImageView;", "tvIndex", "Landroid/widget/TextView;", "getTvIndex", "()Landroid/widget/TextView;", "tvSourceLint", "getTvSourceLint", "netomichatsdk_debug"})
    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final androidx.constraintlayout.widget.ConstraintLayout constRow = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvIndex = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.TextView tvSourceLint = null;
        @org.jetbrains.annotations.NotNull()
        private final android.widget.ImageView imgSource = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.constraintlayout.widget.ConstraintLayout getConstRow() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvIndex() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTvSourceLint() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.ImageView getImgSource() {
            return null;
        }
    }
}